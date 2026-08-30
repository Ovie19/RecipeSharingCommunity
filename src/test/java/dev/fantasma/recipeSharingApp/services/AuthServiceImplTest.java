package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.models.User;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import dev.fantasma.recipeSharingApp.dtos.requests.LoginRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.LogoutRequest;
import dev.fantasma.recipeSharingApp.dtos.requests.RegisterUserRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.LoginResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.LogoutResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RegisterUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;


    @Test
    public void registerUser_userIsSuccessfullyRegistered_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());
        when(userRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterUserResponse response = authServiceImpl.registerUser(registerUserRequest);

        assertEquals("rhemaDestiny", response.getUsername());
        assertEquals("destinyrhema@gmail.com", response.getEmail());
        assertEquals("User registered successfully", response.getMessage());

        verify(userRepository).save(any(User.class));

    }

    @Test
    public void registerUser_withAnExistingMail_throwsException_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        when(userRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,()-> authServiceImpl.registerUser(registerUserRequest),"Email already exists");

        verify(userRepository, never()).save(any());

    }

    @Test
    public void registerUser_withAnExistingUserName_throwsException_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        when(userRepository.findByEmail(registerUserRequest.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(registerUserRequest.getUsername())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,()-> authServiceImpl.registerUser(registerUserRequest),"Username already exists");

        verify(userRepository, never()).save(any());

    }

    @Test
    public void registerUser_loginWithValidUsernameAndPassword_logsInSuccessfully_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        when(userRepository.save(any(User.class))).thenReturn(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword(registerUserRequest.getPassword());

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));

        LoginResponse response = authServiceImpl.login(loginRequest);
        assertTrue(response.isLoggedIn());
        assertEquals("Logged in successfully", response.getMessage());
        assertEquals("rhemaDestiny", response.getUsername());
        verify(userRepository).save(any(User.class));

    }

    @Test
    public void registerUser_loginWithInValidUsername_throwsException_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("rhema");
        loginRequest.setPassword(registerUserRequest.getPassword());

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,()-> authServiceImpl.login(loginRequest),"Username does not exists");
        verify(userRepository, never()).save(any());

    }

    @Test
    public void registerUser_loginWithInValidPassword_throwsException_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerUserRequest.getUsername());
        loginRequest.setPassword("1233");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,()-> authServiceImpl.login(loginRequest),"Invalid password");
        verify(userRepository, never()).save(any());

    }

    @Test
    public void logoutUser_withValidUsername_logoutSuccessfully_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("rhemaDestiny");
        when(userRepository.findByUsername(logoutRequest.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        LogoutResponse logoutResponse = authServiceImpl.logout(logoutRequest);
        assertEquals("Logged out successfully", logoutResponse.getMessage() );
        verify(userRepository).save(any(User.class));

    }

    @Test
    public void logoutUser_withInValidUsername_throwsException_test(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("rhemaDestiny");
        registerUserRequest.setPassword("1234");
        registerUserRequest.setEmail("destinyrhema@gmail.com");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(registerUserRequest.getUsername());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("rhema");
        when(userRepository.findByUsername(logoutRequest.getUsername())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,()-> authServiceImpl.logout(logoutRequest),"Invalid username");
        verify(userRepository, never()).save(any());

    }


}