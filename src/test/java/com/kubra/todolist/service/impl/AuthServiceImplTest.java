package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.PassiveUserException;
import com.kubra.todolist.exception.WrongPasswordException;
import com.kubra.todolist.model.User;
import com.kubra.todolist.request.LoginRequest;
import com.kubra.todolist.response.LoginResponse;
import com.kubra.todolist.service.JwtService;
import com.kubra.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        openMocks(this);

        loginRequest = new LoginRequest("test@example.com", "123456");

        user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("test@example.com")
                .password("encodedPassword")
                .active(true)
                .build();
    }

    @Test
    void loginUser_validCredentials_returnsToken() {
        when(userService.getUserByEmail(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("mocked-token");

        LoginResponse response = authService.loginUser(loginRequest);

        assertNotNull(response);
        assertEquals("mocked-token", response.getToken());
    }

    @Test
    void loginUser_wrongPassword_throwsWrongPasswordException() {
        when(userService.getUserByEmail(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(WrongPasswordException.class, () -> authService.loginUser(loginRequest));
    }

    @Test
    void loginUser_passiveUser_throwsPassiveUserException() {
        user.setActive(false);
        when(userService.getUserByEmail(loginRequest.getEmail())).thenReturn(user);

        assertThrows(PassiveUserException.class, () -> authService.loginUser(loginRequest));
    }
}