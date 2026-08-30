package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;

public interface AuthService {
    RegisterUserResponse registerUser(RegisterUserRequest request) throws RecipeAppException;

    LoginResponse login(LoginRequest request) throws RecipeAppException;

    LogoutResponse logout(LogoutRequest request) throws RecipeAppException;

}
