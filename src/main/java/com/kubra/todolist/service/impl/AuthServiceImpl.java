package com.kubra.todolist.service.impl;

import com.kubra.todolist.exception.PassiveUserException;
import com.kubra.todolist.exception.WrongPasswordException;
import com.kubra.todolist.model.User;
import com.kubra.todolist.request.LoginRequest;
import com.kubra.todolist.response.LoginResponse;
import com.kubra.todolist.service.AuthService;
import com.kubra.todolist.service.JwtService;
import com.kubra.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user.isActive()) {
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user);
                return new LoginResponse(token);
            } else {
                throw new WrongPasswordException();
            }
        } else {
            throw new PassiveUserException();
        }
    }
}
