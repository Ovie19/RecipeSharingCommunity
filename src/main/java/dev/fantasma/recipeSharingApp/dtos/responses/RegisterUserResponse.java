package dev.fantasma.recipeSharingApp.dtos.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterUserResponse {
    private String message;
    private String username;
    private UUID id;
    private String email;
}
