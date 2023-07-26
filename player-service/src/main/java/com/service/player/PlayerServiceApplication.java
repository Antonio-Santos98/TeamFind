package com.service.player;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.net.URL;

@SpringBootApplication
@EnableEurekaClient
public class PlayerServiceApplication {
    public static void main(String [] args){
        SpringApplication.run(PlayerServiceApplication.class, args);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource("com/service/player/events/TeamRequest.class");
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
        System.out.println("Location of TeamRequest: " + resource);
    }
}
