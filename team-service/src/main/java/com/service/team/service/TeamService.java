package com.service.team.service;

import com.service.team.events.TeamRequest;
import com.service.team.events.TeamResponse;
import com.service.team.model.Team;
import com.service.team.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepo teamRepo;
    private final KafkaTemplate<Long, TeamResponse> kafkaTemplate;

    public List<Team> getAllTeams(){
        return teamRepo.findAll();
    }

    public Team getTeamName(String teamName){
        return teamRepo.findByTeamName(teamName);
    }

    public Team createTeam(Team team){
        return teamRepo.save(team);
    }

    public boolean deleteTeam(Long teamId) {
        Team existingTeam = teamRepo.findByTeamId(teamId);
        if (existingTeam != null) {
            teamRepo.delete(existingTeam);
            return true;
        }
        return false;
    }

    public void interactedRequest(Boolean response){

        TeamResponse teamResponse = new TeamResponse();
        teamResponse.setResponse(response);

        kafkaTemplate.send("teamResponse", teamResponse);
    }

    public void saveRequest(TeamRequest teamRequest){
        Long teamId = teamRequest.getTeamId();
        Long userId = teamRequest.getUserId();

        Team team = teamRepo.findByTeamId(teamId);

        List<TeamRequest> teamRequests = team.getTeamRequest();

        boolean existingRequest = teamRequests.stream()
                .anyMatch(request -> request.getUserId().equals(userId));

        if (existingRequest) {
            throw new RuntimeException("Request already sent to team!");
        }
        else{
            teamRequests.add(teamRequest);
            team.setTeamRequest(teamRequests);
            teamRepo.save(team);
        }


    }



}
