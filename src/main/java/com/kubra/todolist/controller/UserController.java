package com.kubra.todolist.controller;

import com.kubra.todolist.request.UserRequest;
import com.kubra.todolist.request.UserStatusUpdateRequest;
import com.kubra.todolist.response.UserResponse;
import com.kubra.todolist.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User API")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userRequest));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/updateActiveOrPassiveUser")
    public ResponseEntity<Void> updateActiveOrPassiveUser(@RequestBody UserStatusUpdateRequest userStatusUpdateRequest) {
        userService.updateActiveOrPassiveUser(userStatusUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
