package com.service.team.controller;

import com.service.team.events.TeamRequest;
import com.service.team.model.Team;
import com.service.team.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams(){
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/{response}")
    public void requestResponse(@PathVariable Boolean response){
        teamService.interactedRequest(response);
    }


    @KafkaListener(topics = "teamRequest")
    public void handleTeamRequest(TeamRequest teamRequest){
        teamService.saveRequest(teamRequest);
    }

}
