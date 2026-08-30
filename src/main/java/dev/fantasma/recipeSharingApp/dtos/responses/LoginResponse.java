package dev.fantasma.recipeSharingApp.dtos.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private String username;
    private boolean isLoggedIn;
}
