package com.kubra.todolist.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusUpdateRequest {
    @NotBlank(message = "Email cannot empty")
    private String email;
    @NotBlank(message = "Active cannot empty")
    private boolean active;
}
