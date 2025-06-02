package com.kubra.todolist.service;

import com.kubra.todolist.request.LoginRequest;
import com.kubra.todolist.response.LoginResponse;

public interface AuthService {

    LoginResponse loginUser(LoginRequest loginRequest);
}
