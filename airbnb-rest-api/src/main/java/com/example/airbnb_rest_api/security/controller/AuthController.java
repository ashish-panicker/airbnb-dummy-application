package com.example.airbnb_rest_api.security.controller;

import com.example.airbnb_rest_api.model.User;
import com.example.airbnb_rest_api.security.dto.LoginRequest;
import com.example.airbnb_rest_api.security.dto.RegisterRequest;
import com.example.airbnb_rest_api.security.service.AuthService;
import com.example.airbnb_rest_api.security.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        if (authService.findByUserName(request.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        var user = new User(
                request.name(),
                passwordEncoder.encode(request.password()),
                request.email(),
                Set.of(request.role().toUpperCase()),
                request.phoneNumber()
        );
        authService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (authService.findByUserName(request.userName()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exists");
        }
        var unAuthenticatedUser = new UsernamePasswordAuthenticationToken(
                request.userName(), request.password()
        );
        Authentication authenticatedUser =
                authenticationManager.authenticate(unAuthenticatedUser);
        String token = jwtUtil.generateToken((UserDetails) authenticatedUser.getPrincipal());
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK, "token", token));

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthException(AuthenticationException ae) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ae.getMessage());
    }
}
