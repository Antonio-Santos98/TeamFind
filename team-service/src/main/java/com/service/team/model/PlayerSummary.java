package com.service.team.model;

import lombok.*;

import javax.persistence.*;

@Data
@Table(name = "player_summary")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlayerSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String role;

}
