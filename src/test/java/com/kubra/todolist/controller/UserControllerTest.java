package com.kubra.todolist.controller;

import com.kubra.todolist.request.UserRequest;
import com.kubra.todolist.request.UserStatusUpdateRequest;
import com.kubra.todolist.response.UserResponse;
import com.kubra.todolist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setEmail("test@example.com");
        userRequest.setName("Test");
        userRequest.setSurname("User");

        userResponse = new UserResponse();
        userResponse.setId("123");
        userResponse.setEmail("test@example.com");
        userResponse.setName("Test");
        userResponse.setSurname("User");
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.createUser(userRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponse, response.getBody());
        verify(userService).createUser(userRequest);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.updateUser(userRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponse, response.getBody());
        verify(userService).updateUser(userRequest);
    }

    @Test
    void testGetUser() {
        String userId = "123";
        when(userService.getUser(userId)).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = userController.getUser(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponse, response.getBody());
        verify(userService).getUser(userId);
    }

    @Test
    void testGetAllUsers() {
        List<UserResponse> users = List.of(userResponse);
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(users, response.getBody());
        verify(userService).getAllUsers();
    }

    @Test
    void testUpdateActiveOrPassiveUser() {
        UserStatusUpdateRequest updateRequest = new UserStatusUpdateRequest();
        updateRequest.setActive(false);

        ResponseEntity<Void> response = userController.updateActiveOrPassiveUser(updateRequest);

        assertEquals(200, response.getStatusCode().value());
        verify(userService).updateActiveOrPassiveUser(updateRequest);
    }
}
