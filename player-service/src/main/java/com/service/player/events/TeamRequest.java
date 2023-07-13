package com.service.player.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teamRequests")
public class TeamRequest {

    @Id
    private Long requestId;
    private Long userId;
    private String userName;
    private String role;
    private Long teamId;

}
