package com.service.player.repo;

import com.service.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepo extends JpaRepository<Player,Long> {

    Player findByUserName(String userName);

    Player findByUserId(Long userId);
}
