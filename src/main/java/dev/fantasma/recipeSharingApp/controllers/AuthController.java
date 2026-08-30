package dev.fantasma.recipeSharingApp.controllers;

import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.ApiResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;
import dev.fantasma.recipeSharingApp.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@RequestBody RegisterUserRequest request){
        try {
            RegisterUserResponse response = authService.registerUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(RecipeAppException ex) {
            return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest request){
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(new ApiResponse(response, true));
        } catch(RecipeAppException ex) {
            return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false), BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?>logout(@RequestBody LogoutRequest request){
        try {
            LogoutResponse response = authService.logout(request);
            return ResponseEntity.ok(new ApiResponse(response, true));
        } catch(RecipeAppException ex) {
            return new ResponseEntity<>(new ApiResponse(ex.getMessage(), false), BAD_REQUEST);
        }
    }
}


