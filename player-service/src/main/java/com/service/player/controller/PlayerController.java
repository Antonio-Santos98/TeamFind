package com.service.player.controller;

import com.service.player.model.Player;
import com.service.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/request/{userId}")
    public ResponseEntity<Player> playerTeamRequest(Player player, @PathVariable Player userId){
        Player requestee = playerService.findPlayerUser(player.getUserName());
        userId = playerService.findById(player.getUserId());
        System.out.println(userId);


        return ResponseEntity.ok(playerService.requestTeam(requestee));
    }
}
