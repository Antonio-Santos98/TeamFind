package com.service.team.controller;

import com.service.team.events.TeamRequest;
import com.service.team.model.Team;
import com.service.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/find")
    public ResponseEntity<Team> getTeamByName(@RequestBody Team team){
        return ResponseEntity.ok(teamService.getTeamName(team.getTeamName()));
    }

    @PostMapping("/{response}")
    public void requestResponse(@PathVariable Boolean response, @RequestBody TeamRequest teamRequest){
        teamService.interactedRequest(response, teamRequest);
    }

    @PatchMapping("/{teamId}")
    public ResponseEntity<?> patchTeam(@PathVariable Long teamId, @RequestBody Map<String, Object> updates){
        return new ResponseEntity<>(
                teamService.patchTeam(updates, teamId),
                HttpStatus.ACCEPTED
        );
    }


    @KafkaListener(topics = "teamRequest")
    public void handleTeamRequest(TeamRequest teamRequest)  {
        teamService.saveRequest(teamRequest);
    }


    @PostMapping("/new")
    public ResponseEntity<String> newTeam(@RequestBody Team team){
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Boolean> deleteTeam(@PathVariable Long teamId){
        return ResponseEntity.ok(teamService.deleteTeam(teamId));
    }

}
