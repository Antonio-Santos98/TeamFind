package com.service.player.service;

import com.service.player.events.TeamRequest;
import com.service.player.events.TeamResponse;
import com.service.player.exceptions.PlayerExistsException;
import com.service.player.exceptions.PlayerNotFound;
import com.service.player.model.Player;
import com.service.player.model.Team;
import com.service.player.repo.PlayerRepo;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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

    public String createPlayer(Player newPlayer){
        if(playerRepo.findByUserName(newPlayer.getUserName()) != null){
            throw new PlayerExistsException();
        }
        else{
            playerRepo.save(newPlayer);
            return "Player Saved!";
        }
    }

    public String patchPlayer(Map<String, Object> updates, Long userId){
        Player playerToPatch = playerRepo.findById(userId)
                .orElseThrow(() -> new PlayerNotFound(userId));

        updates.forEach((k,o) -> {
            try{
                Field nameField = playerToPatch.getClass().getDeclaredField(k);
                nameField.setAccessible(true);
                nameField.set(playerToPatch, o);
            }catch(NoSuchFieldException | IllegalAccessException e){
                throw new PlayerNotFound(userId);
            }
        });

        if(playerRepo.findByUserName(playerToPatch.getUserName()) != null){
            throw new PlayerExistsException();
        }else{
            playerRepo.save(playerToPatch);
            return "Updates Applied!";
        }
    }

    public String requestTeam(Player player, Team team){
        TeamRequest newRequest = new TeamRequest();

        if(team.getPlayersNeeded() > 0){
            newRequest.setUserId(player.getUserId());
            newRequest.setUserName(player.getUserName());
            newRequest.setRole(player.getPreferredRole());
            newRequest.setTeam(team);
            kafkaTemplate.send("teamRequest", newRequest);
            return "Request Sent!";
        }else{
            return "Requested Team of " + team.getTeamName() + " does not need more players";
        }

    }

    public String addToTeam(TeamResponse teamResponse){
        if(teamResponse.getResponse()){
            Player requestedPlayer = playerRepo.findByUserId(teamResponse.getTeamRequest().getUserId());
            requestedPlayer.setTeamId(teamResponse.getTeamRequest().getTeam().getTeamId());
            playerRepo.save(requestedPlayer);
            return "Player Has been added to Team!";
        }
        return "There was an error when adding player to team";
    }

}
