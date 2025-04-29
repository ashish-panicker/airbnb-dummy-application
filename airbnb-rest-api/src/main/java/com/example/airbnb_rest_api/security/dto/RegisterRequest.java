package com.example.airbnb_rest_api.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        @NotEmpty @NotBlank @NotNull String name,
        @NotEmpty @NotBlank @NotNull @Email String email,
        @NotEmpty @NotBlank @NotNull @Length(min = 6) String password,
        @NotEmpty @NotBlank @NotNull String role,
        @NotEmpty @NotBlank @NotNull @Length(min = 10, max = 10) String phoneNumber) {
}
