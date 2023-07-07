package player.service;

import player.model.Player;
import player.repo.PlayerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepo playerRepo;

    public List<Player> getAllPlayers(){
        return playerRepo.findAll();
    }
}
