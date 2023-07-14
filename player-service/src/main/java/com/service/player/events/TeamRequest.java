package com.service.player.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {

    private Long userId;
    private String userName;
    private String role;
    private Long teamId;

}
