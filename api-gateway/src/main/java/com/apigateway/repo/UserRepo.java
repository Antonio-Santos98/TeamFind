package com.apigateway.repo;

import com.apigateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    @Query("SELECT u.password FROM User u WHERE u.userName = ?1")
    String findEncodedPasswordByUser(String username);
}

