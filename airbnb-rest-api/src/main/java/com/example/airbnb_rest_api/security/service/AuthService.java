package com.example.airbnb_rest_api.security.service;

import com.example.airbnb_rest_api.model.User;
import com.example.airbnb_rest_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public Optional<User> findByUserName(String userName) {
        return userService.findByUserName(userName);
    }

    public User save(User user) {
        return userService.save(user);
    }
}
