package com.service.team.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String teamId;
    private String teamName;
    private String standing;
    private List<PlayerSummary> playerList;
    private Boolean needPlayers;
    private Integer playersNeeded;
}
