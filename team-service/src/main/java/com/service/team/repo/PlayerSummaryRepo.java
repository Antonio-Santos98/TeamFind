package com.service.team.repo;

import com.service.team.model.PlayerSummary;
import org.springframework.data.repository.CrudRepository;

public interface PlayerSummaryRepo extends CrudRepository<PlayerSummary, Long> {
}
