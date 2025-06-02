package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.UserAvailableException;
import com.kubra.todolist.exception.UserNotFoundException;
import com.kubra.todolist.mapper.UserMapper;
import com.kubra.todolist.model.User;
import com.kubra.todolist.repository.UserRepository;
import com.kubra.todolist.request.UserRequest;
import com.kubra.todolist.request.UserStatusUpdateRequest;
import com.kubra.todolist.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        openMocks(this);
        userRequest = new UserRequest("test-id", "Test", "User", "test@example.com", "password",true);
        user = User.builder()
                .id("test-id")
                .name("Test")
                .surname("User")
                .email("test@example.com")
                .password("encoded")
                .active(true)
                .build();
        userResponse = new UserResponse("test-id", "Test", "User", "test@example.com","123456",true);
    }

    @Test
    void createUser_success() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse response = userService.createUser(userRequest);

        assertNotNull(response);
        assertEquals(userResponse.getEmail(), response.getEmail());
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        assertThrows(UserAvailableException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void updateUser_success() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("new-encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse response = userService.updateUser(userRequest);

        assertNotNull(response);
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userRequest));
    }

    @Test
    void getUser_success() {
        when(userRepository.findById("test-id")).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(user)).thenReturn(userResponse);

        UserResponse response = userService.getUser("test-id");
        assertEquals(userResponse.getEmail(), response.getEmail());
    }

    @Test
    void getUser_userNotFound_throwsException() {
        when(userRepository.findById("test-id")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser("test-id"));
    }

    @Test
    void updateActiveOrPassiveUser_success() {
        UserStatusUpdateRequest request = new UserStatusUpdateRequest("test@example.com", false);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        userService.updateActiveOrPassiveUser(request);
        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.usersToUserResponses(List.of(user))).thenReturn(List.of(userResponse));

        List<UserResponse> responses = userService.getAllUsers();
        assertEquals(1, responses.size());
    }
}