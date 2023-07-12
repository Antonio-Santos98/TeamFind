package com.notifications;

import com.notifications.events.TeamRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class NotificationServiceApplication {
    public static void main(String [] args){
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "teamRequest")
    public void handleTeamRequest(TeamRequest teamRequest){
        log.info("Received Player Info - {} {}", teamRequest.getUserId(), teamRequest.getUserName());
    }
}
