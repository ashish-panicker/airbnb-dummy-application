package com.example.airbnb_rest_api.service;

import com.example.airbnb_rest_api.model.User;
import com.example.airbnb_rest_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByEmail(userName);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
