package com.service.team.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Table(name = "playerRequest")
public class TeamRequest {
    private Long userId;
    private String userName;
    private String role;
    private Long teamId;
}
