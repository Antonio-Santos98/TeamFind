package com.apigateway.controller;

import com.apigateway.model.User;
import com.apigateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user){
        return new ResponseEntity<>(
            userService.login(user),
            HttpStatus.ACCEPTED
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody User user){
        return new ResponseEntity<>(
          userService.register(user),
          HttpStatus.ACCEPTED
        );
    }

}
