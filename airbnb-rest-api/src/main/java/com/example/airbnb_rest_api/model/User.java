package com.example.airbnb_rest_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int userId;

    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(name = "email_verified_at")
    private LocalDate emailVerifiedAt;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String description;

    @Column(name = "profile_image")
    private String profileImage;

    private boolean isExpired = false;

    private boolean isLocked = false;

    @Column(name = "credentials_expired")
    private boolean isCredentialsExpired = false;

    private boolean isEnabled = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    public User(String name, String password, String email, Set<String> roles, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
    }
}
