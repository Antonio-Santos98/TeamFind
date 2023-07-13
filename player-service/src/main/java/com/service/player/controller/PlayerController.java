package com.service.player.controller;

import com.service.player.model.Player;
import com.service.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/request/{userId}/{teamId}")
    public CompletableFuture<String> playerTeamRequest(@PathVariable Long userId, @PathVariable Long teamId){
        Player requestee = playerService.findById(userId);
        return CompletableFuture.supplyAsync(() -> playerService.requestTeam(requestee, teamId));
    }
}
