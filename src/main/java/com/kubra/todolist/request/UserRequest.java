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
public class UserRequest {
    private String id;
    @NotBlank(message = "Name cannot empty")
    private String name;
    @NotBlank(message = "Surname cannot empty")
    private String surname;
    private String email;
    @NotBlank(message = "Password cannot empty")
    private String password;
    private boolean active;
}
