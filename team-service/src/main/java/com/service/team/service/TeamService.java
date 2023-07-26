package com.service.team.service;

import com.service.team.events.TeamRequest;
import com.service.team.events.TeamResponse;
import com.service.team.exceptions.RequestExistsException;
import com.service.team.exceptions.TeamExistsException;
import com.service.team.exceptions.TeamNotFound;
import com.service.team.model.PlayerSummary;
import com.service.team.model.Team;
import com.service.team.repo.PlayerSummaryRepo;
import com.service.team.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepo teamRepo;
    private final PlayerSummaryRepo playerSummaryRepo;
    private final KafkaTemplate<Long, TeamResponse> kafkaTemplate;

    public List<Team> getAllTeams() {
        return teamRepo.findAll();
    }

    public Team getTeamName(String teamName) {
        return teamRepo.findByTeamName(teamName);
    }

    public String createTeam(Team team) {
        if(teamRepo.findByTeamName(team.getTeamName()) != null){
             throw new TeamExistsException();
        }
        else{
            teamRepo.save(team);
            return "Saved Team!";
        }
    }

    public String patchTeam(Map<String, Object> updates, Long teamId){
        Team teamToPatch = teamRepo.findById(teamId).orElseThrow(() -> new TeamNotFound(teamId));

        updates.forEach((k,o) -> {
            try{
                Field nameField = teamToPatch.getClass().getDeclaredField(k);
                nameField.setAccessible(true);
                nameField.set(teamToPatch, o);
            }catch(NoSuchFieldException | IllegalAccessException e){
                throw new TeamNotFound(teamId);
            }
        });

        if(teamRepo.findByTeamName(teamToPatch.getTeamName()) != null){
            throw new TeamExistsException();
        }
        else{
            teamRepo.save(teamToPatch);
            return "Updates Applied!";
        }
    }

    public boolean deleteTeam(Long teamId) {
        Team existingTeam = teamRepo.findByTeamId(teamId);
        if (existingTeam != null) {
            teamRepo.delete(existingTeam);
            return true;
        }
        return false;
    }

    public void interactedRequest(Boolean response, TeamRequest teamRequest) {
        TeamResponse teamResponse = new TeamResponse();
        Team existingTeam = teamRepo.findByTeamId(teamRequest.getTeam().getTeamId());

        if (response) {
            //player
            PlayerSummary newPlayer = new PlayerSummary();
            newPlayer.setUserId(teamRequest.getUserId());
            newPlayer.setUserName(teamRequest.getUserName());
            newPlayer.setRole(teamRequest.getRole());
            playerSummaryRepo.save(newPlayer);
            existingTeam.getPlayerList().add(newPlayer);
            existingTeam.setPlayersNeeded(existingTeam.getPlayersNeeded() - 1);

            //team
            teamResponse.setResponse(response);
            teamResponse.setTeamRequest(teamRequest);
            kafkaTemplate.send("teamResponse", teamResponse);
        }

        // Delete request by userId
        teamRepo.deleteTeamRequestByUserId(teamRequest.getUserId());

        teamRepo.save(existingTeam);
    }


    public String saveRequest(TeamRequest teamRequest)  {
        if (teamRequest.getTeam() != null) {
            Team requestedTeam = teamRepo.findByTeamId(teamRequest.getTeam().getTeamId());
            if(requestedTeam.getPlayersNeeded() > 0){
                List<TeamRequest> teamRequests = requestedTeam.getTeamRequest();

                for(TeamRequest existingRequests: teamRequests){
                    if(existingRequests.getUserId().equals(teamRequest.getUserId()) && existingRequests.getTeam().getTeamId().equals(teamRequest.getTeam().getTeamId())){
                        throw new RequestExistsException();
                    }
                }
                teamRequests.add(teamRequest);
                teamRequest.getTeam().setTeamRequest(teamRequests);
                teamRepo.save(teamRequest.getTeam());
                return "Request Sent!";
            }else{
                return "Team - " + requestedTeam.getTeamName() + " does not need any players";
            }
        } else {
            throw new RuntimeException("Team requested was Null!");
        }
    }


}
