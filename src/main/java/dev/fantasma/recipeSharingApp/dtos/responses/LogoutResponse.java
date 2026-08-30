package dev.fantasma.recipeSharingApp.dtos.responses;

import lombok.Data;

@Data
public class LogoutResponse {
    private String message;
    private String username;
}
