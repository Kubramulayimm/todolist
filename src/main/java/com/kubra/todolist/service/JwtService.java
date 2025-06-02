package com.kubra.todolist.service;

import com.kubra.todolist.model.User;

public interface JwtService {

    String generateToken(User user);
}
