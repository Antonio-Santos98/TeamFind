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
        PlayerSummary tone = new PlayerSummary();
        tone.setRole("Rifler");
        tone.setUserName("Tone");
        PlayerSummary jawn = new PlayerSummary();
        jawn.setRole("IGL");
        jawn.setUserName("Jawn");

        List<PlayerSummary> test = new ArrayList<>(Arrays.asList(tone, jawn));

        Team team2 = new Team();
        team2.setTeamName("TestTeam");
        team2.setPlayersNeeded(4);
        team2.setStanding("5-5");
        team2.setPlayerList(test);

        teamRepo.save(team2);
    }
}
