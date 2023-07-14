package com.service.team.service;

import com.service.team.events.TeamRequest;
import com.service.team.events.TeamResponse;
import com.service.team.model.PlayerSummary;
import com.service.team.model.Team;
import com.service.team.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepo teamRepo;
    private final KafkaTemplate<Long, TeamResponse> kafkaTemplate;

    public List<Team> getAllTeams() {
        return teamRepo.findAll();
    }

    public Team getTeamName(String teamName) {
        return teamRepo.findByTeamName(teamName);
    }

    public Team createTeam(Team team) {
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

    public void interactedRequest(Boolean response, TeamRequest teamRequest) {
        TeamResponse teamResponse = new TeamResponse();
        Team requestedTeam = teamRepo.findByTeamId(teamRequest.getTeamId());
        System.out.println("TeamId = " + teamRepo.findByTeamId(teamRequest.getTeamId()));

        if (response) {
            List<PlayerSummary> teamList = requestedTeam.getPlayerList();
            //player
            PlayerSummary newPlayer = new PlayerSummary();
            newPlayer.setUserId(teamRequest.getUserId());
            newPlayer.setUserName(teamRequest.getUserName());
            newPlayer.setRole(teamRequest.getRole());

            //team
            teamResponse.setResponse(response);
            teamResponse.setTeamRequest(teamRequest);
            kafkaTemplate.send("teamResponse", teamResponse);
            teamList.add(newPlayer);
            teamRepo.save(requestedTeam);
        }

        // Delete request by userId
        Team respondingTeam = teamRepo.findByTeamId(teamRequest.getTeamId());
        Long userId = teamRequest.getUserId();

        Optional<TeamRequest> currentReq = respondingTeam.getTeamRequest().stream()
                .filter(request -> request.getUserId().equals(userId))
                .findFirst();

        // Find the request by userId and delete it
        if (currentReq.isPresent()) {
            TeamRequest requestToDelete = currentReq.get();
            respondingTeam.getTeamRequest().remove(requestToDelete);
            teamRepo.save(respondingTeam);
            // The request is removed from the team's list of requests
        } else {
            System.out.println("UserID of " + userId + " was not found :(");
        }
    }

    public void saveRequest(TeamRequest teamRequest) {
        Long teamId = teamRequest.getTeamId();
        Long userId = teamRequest.getUserId();

        Team team = teamRepo.findByTeamId(teamId);

        if (team != null) {
            List<TeamRequest> teamRequests = team.getTeamRequest();

            boolean existingRequest = teamRequests.stream()
                    .anyMatch(request -> request.getUserId().equals(userId));

            if (existingRequest) {
                throw new RuntimeException("Request already sent to team!");
            } else {
                teamRequests.add(teamRequest);
                team.setTeamRequest(teamRequests);
                teamRepo.save(team);
            }
        } else {
            throw new RuntimeException("Team not found for the given teamId!");
        }


    }


}
