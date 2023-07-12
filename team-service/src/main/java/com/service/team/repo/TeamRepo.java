package com.service.team.repo;

import com.service.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepo extends JpaRepository<Team,Long> {

    Team findByTeamName(String teamName);
}
