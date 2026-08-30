package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;

public interface AuthService {
    RegisterUserResponse registerUser(RegisterUserRequest request);

    LoginResponse login(LoginRequest request);

    LogoutResponse logout(LogoutRequest request);

}
