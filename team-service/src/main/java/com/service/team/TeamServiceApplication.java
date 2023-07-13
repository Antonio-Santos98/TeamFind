package com.service.team;

import com.service.team.events.TeamRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableEurekaClient
public class TeamServiceApplication {
    public static void main(String [] args){
        SpringApplication.run(TeamServiceApplication.class, args);
    }

}
