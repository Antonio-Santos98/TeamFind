package com.service.player.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {
    private Boolean response;
    private TeamRequest teamRequest;
}
