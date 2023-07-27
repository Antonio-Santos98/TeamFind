package com.apigateway.service;

import com.apigateway.exceptions.IncorrectPasswordException;
import com.apigateway.exceptions.UserExists;
import com.apigateway.exceptions.UserNotFound;
import com.apigateway.model.User;
import com.apigateway.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String login(User user){
        User attemptUser = userRepo.findByUserName(user.getUserName());
        if(attemptUser != null)
        {
            if(!encoder.matches(user.getPassword(), attemptUser.getPassword())){
                throw new IncorrectPasswordException();
            }else{
                return "Successfully Logged In!";
            }
        }else{
            throw new UserNotFound(user.getUserName());
        }
    }

    public String register(User user){
        if(userRepo.findByUserName(user.getUserName()) == null){
            User newUser = new User();
            newUser.setUserName(user.getUserName());
            newUser.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(newUser);
            return "You have been registered successfully!";
        }else{
            throw new UserExists(user.getUserName());
        }
    }
}
