package com.service.team.model;

import com.service.team.events.TeamRequest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private String standing;
    private Integer playersNeeded;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PlayerSummary> playerList;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "playerRequest")
    private List<TeamRequest> teamRequest;
}
