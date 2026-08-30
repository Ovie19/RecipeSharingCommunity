package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.models.User;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request){
        Optional<User> foundUserViaMail = userRepository.findByEmail(request.getEmail());
        if(foundUserViaMail.isPresent()) throw new IllegalArgumentException("Email already exists");

        Optional<User> foundUserViaUsername = userRepository.findByUsername(request.getUsername());
        if(foundUserViaUsername.isPresent()) throw new IllegalArgumentException("Username already exists");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        User savedUser =userRepository.save(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setMessage("User registered successfully");
        response.setId(savedUser.getId());
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request){
        Optional<User> foundUser = userRepository.findByUsername(request.getUsername());
        if(foundUser.isEmpty()) throw new IllegalArgumentException("Username does not exists");
        User user = foundUser.get();
        if(!user.getPassword().equals(request.getPassword())) throw new IllegalArgumentException("Invalid password");
        user.setLoggedIn(true);
        user = userRepository.save(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setMessage("Logged in successfully");
        loginResponse.setLoggedIn(user.isLoggedIn());

        return loginResponse;
    }

    @Override
    public LogoutResponse logout(LogoutRequest request){
        Optional<User> foundUser = userRepository.findByUsername(request.getUsername());
        if(foundUser.isEmpty()) throw new IllegalArgumentException("Invalid username");
        User user = foundUser.get();
        user.setLoggedIn(false);
        user = userRepository.save(user);

        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setUsername(user.getUsername());
        logoutResponse.setMessage("Logged out successfully");
        return logoutResponse;
    }


}
