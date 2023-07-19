package com.service.player.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.service.player.events.TeamRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private String standing;
    private Integer playersNeeded;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> playerList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", fetch = FetchType.EAGER)
    private List<TeamRequest> teamRequest;
}
