package com.service.team.repo;

import com.service.team.model.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TeamRepo extends JpaRepository<Team,Long> {

    Team findByTeamName(String teamName);

    @EntityGraph(attributePaths = "teamRequest")
    Team findByTeamId(Long id);


    @Query("SELECT t FROM Team t JOIN t.teamRequest tr WHERE tr.userId = :userId")
    List<Team> findTeamsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM TeamRequest tr WHERE (tr.userId = :userId AND tr.requestId <> 0)")
    @Transactional
    void deleteTeamRequestByUserId(@Param("userId") Long userId);

}
