package com.kubra.todolist.service;

import com.kubra.todolist.model.User;
import com.kubra.todolist.request.UserRequest;
import com.kubra.todolist.request.UserStatusUpdateRequest;
import com.kubra.todolist.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest);

    UserResponse getUser(String userId);

    User getUserByEmail(String email);

    List<UserResponse> getAllUsers();

    void updateActiveOrPassiveUser(UserStatusUpdateRequest userStatusUpdateRequest);
}
