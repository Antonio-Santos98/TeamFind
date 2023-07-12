package com.service.team.util;

import com.service.team.model.PlayerSummary;
import com.service.team.model.Team;
import com.service.team.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final TeamRepo teamRepo;

    @Override
    public void run(String... args){
        PlayerSummary zyw = new PlayerSummary();
        zyw.setUserName("Zywoo");
        zyw.setRole("Awper");
        PlayerSummary apex = new PlayerSummary();
        apex.setUserName("apEX");
        apex.setRole("IGL");
        PlayerSummary mag = new PlayerSummary();
        mag.setUserName("Magisk");
        mag.setRole("Rifler");
        PlayerSummary fla = new PlayerSummary();
        fla.setUserName("flameZ");
        fla.setRole("Rifler");
        PlayerSummary spi = new PlayerSummary();
        spi.setUserName("Spinx");
        spi.setRole("Support");

        List<PlayerSummary> vitList = new ArrayList<>(Arrays.asList(zyw,apex,mag,fla,spi));

        Team team1 = new Team();
        team1.setTeamName("Vitality");
        team1.setPlayersNeeded(0);
        team1.setStanding("11-1");
        team1.setPlayerList(vitList);

        teamRepo.save(team1);
    }
}
