package com.kubra.todolist.controller;

import com.kubra.todolist.request.LoginRequest;
import com.kubra.todolist.response.LoginResponse;
import com.kubra.todolist.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_shouldReturnToken() {
        // Arrange
        LoginRequest request = new LoginRequest("cagri@example.com", "123457");
        LoginResponse expectedResponse = new LoginResponse("mock-token");

        when(authService.loginUser(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<LoginResponse> response = authController.loginUser(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("mock-token", response.getBody().getToken());

        verify(authService, times(1)).loginUser(request);
    }

    @Test
    void testLogout_shouldReturnSuccessMessage() {
        ResponseEntity<String> response = authController.logout();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Çıkış başarılı.", response.getBody());
    }
}