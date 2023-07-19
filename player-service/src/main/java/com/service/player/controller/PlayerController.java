package com.service.player.controller;

import com.service.player.events.TeamResponse;
import com.service.player.model.Player;
import com.service.player.model.Team;
import com.service.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(){
        List<Player> players = playerService.getAllPlayers();

        return ResponseEntity.ok(players);
    }

    @GetMapping("/find")
    public ResponseEntity<Player> getPlayerByUser(@RequestBody Player player){
        return ResponseEntity.ok(playerService.findPlayerUser(player.getUserName()));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> patchPlayer(@PathVariable Long userId, @RequestBody Map<String,Object> updates){
        return new ResponseEntity<>(
                playerService.patchPlayer(updates, userId),
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping
    public ResponseEntity<String> createPlayer(@RequestBody Player player){
        player.setUserName(player.getUserName());
        player.setPreferredRole(player.getPreferredRole());
        player.setPlayerRank(player.getPlayerRank());
        player.setRating(player.getRating());
        return ResponseEntity.ok(playerService.createPlayer(player));
    }

    @PostMapping("/request/{userId}")
    public CompletableFuture<String> playerTeamRequest(@PathVariable Long userId, @RequestBody Team team){
        Player requestPlay = playerService.findById(userId);
        return CompletableFuture.supplyAsync(() -> playerService.requestTeam(requestPlay, team));
    }

    @KafkaListener(topics = "teamResponse")
    public void handleResponse(TeamResponse teamResponse){
       playerService.addToTeam(teamResponse);
    }
}
