package dev.fantasma.recipeSharingApp.controllers;

import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;
import dev.fantasma.recipeSharingApp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse>registerUser(@RequestBody RegisterUserRequest request){
        RegisterUserResponse response = authService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse>logout(@RequestBody LogoutRequest request){
        return ResponseEntity.ok(authService.logout(request));
    }
}
