package com.service.player.service;

import com.service.player.events.TeamRequest;
import com.service.player.model.Player;
import com.service.player.repo.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepo playerRepo;
    private final KafkaTemplate<Long, TeamRequest> kafkaTemplate;

    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }

    public Player findPlayerUser(String userName){
        return playerRepo.findByUserName(userName);
    }

    public Player findById(Long userId){
        return playerRepo.findByUserId(userId);
    }

    public Player createPlayer(Player newPlayer){
        return playerRepo.save(newPlayer);
    }

    public Player requestTeam(Player player){
        Player requestee = playerRepo.findByUserName(player.getUserName());
        TeamRequest newRequest = new TeamRequest();
        newRequest.setUserId(requestee.getUserId());
        newRequest.setUserName(requestee.getUserName());
        newRequest.setRole(requestee.getPreferredRole());

        kafkaTemplate.send("requestTeam", newRequest.getUserId(),newRequest);

        return requestee;
    }

}
