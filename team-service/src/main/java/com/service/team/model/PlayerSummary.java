package com.service.team.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String userName;
    private String role;

}
