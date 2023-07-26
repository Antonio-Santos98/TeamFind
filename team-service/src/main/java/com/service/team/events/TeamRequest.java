package com.service.team.events;

import com.service.team.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_requests")
public class TeamRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7819903795119368731L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private Long userId;
    private String userName;
    private String role;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
