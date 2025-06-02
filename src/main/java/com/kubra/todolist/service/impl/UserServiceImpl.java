package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.UserAvailableException;
import com.kubra.todolist.exception.UserNotFoundException;
import com.kubra.todolist.mapper.UserMapper;
import com.kubra.todolist.model.User;
import com.kubra.todolist.repository.UserRepository;
import com.kubra.todolist.request.UserRequest;
import com.kubra.todolist.request.UserStatusUpdateRequest;
import com.kubra.todolist.response.UserResponse;
import com.kubra.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {
        String encode = passwordEncoder.encode(userRequest.getPassword());

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserAvailableException();
        }

        User user = User.builder()
                .id(UUID.randomUUID()
                        .toString())
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .password(encode)
                .active(Boolean.TRUE)
                .build();
        User saveUser = userRepository.save(user);
        return userMapper.userToUserResponse(saveUser);
    }

    public UserResponse updateUser(UserRequest userRequest) {
        if (userRequest.getEmail() == null || userRequest.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank!");
        }
        User user = userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (userRequest.getName() != null) {
            user.setName(userRequest.getName());
        }
        if (userRequest.getSurname() != null) {
            user.setSurname(userRequest.getSurname());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public UserResponse getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.userToUserResponse(user);

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

    }

    public void updateActiveOrPassiveUser(UserStatusUpdateRequest userStatusUpdateRequest) {
        User user = userRepository.findByEmail(userStatusUpdateRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);
        user.setActive(userStatusUpdateRequest.isActive());
        userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> userAll = userRepository.findAll();
        return userMapper.usersToUserResponses(userAll);
    }

}