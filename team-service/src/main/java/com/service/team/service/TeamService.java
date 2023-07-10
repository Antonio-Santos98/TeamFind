package com.service.team.service;

import com.service.team.model.PlayerSummary;
import com.service.team.model.Team;
import com.service.team.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepo teamRepo;

    public List<Team> getAllTeams(){
        return teamRepo.findAll();
    }

    public Team getTeamName(String teamName){
        return teamRepo.findByTeamName(teamName);
    }

    public Team createTeam(Team team){
        return teamRepo.save(team);
    }


    public boolean deleteTeam(String teamId) {
        Team existingTeam = teamRepo.findById(teamId).orElse(null);
        if (existingTeam != null) {
            teamRepo.delete(existingTeam);
            return true;
        }
        return false;
    }

}
