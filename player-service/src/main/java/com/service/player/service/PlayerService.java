package com.service.player.service;

import com.service.player.model.Player;
import com.service.player.repo.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepo playerRepo;

    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }

    public Player findPlayerUser(String userName){
        return playerRepo.findByUserName(userName);
    }

    public Player createPlayer(Player newPlayer){
        return playerRepo.save(newPlayer);
    }

}
