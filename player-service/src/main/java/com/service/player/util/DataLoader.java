package com.service.player.util;

import com.service.player.model.Player;
import com.service.player.repo.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final PlayerRepo playerRepo;

    @Override
    public void run(String... args){
        Player player1 = new Player();
        player1.setUserName("user1");
        player1.setPreferredRole("Rifler");
        player1.setRating(5);
        player1.setRank("Level 10");
        player1.setNeedTeam(true);

        Player player2 = new Player();
        player2.setUserName("user2");
        player2.setPreferredRole("Awper");
        player2.setRating(3);
        player2.setRank("Level 8");
        player2.setNeedTeam(true);

        Player player3 = new Player();
        player3.setUserName("user3");
        player3.setPreferredRole("Entry");
        player3.setRating(4);
        player3.setRank("Level 10");
        player3.setNeedTeam(false);

        playerRepo.save(player1);
        playerRepo.save(player2);
        playerRepo.save(player3);
    }
}
