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

        Team team1 = new Team();
        team1.setTeamName("TestTeam");
        team1.setPlayersNeeded(4);
        team1.setStanding("5-5");

        Team team2 = new Team();
        team2.setTeamName("ShreeSports");
        team2.setPlayersNeeded(5);
        team2.setStanding("1-4");

        Team team3 = new Team();
        team3.setTeamName("FaZe");
        team3.setPlayersNeeded(0);
        team3.setStanding("15-0");

        teamRepo.save(team1);
        teamRepo.save(team2);
        teamRepo.save(team3);
    }
}
