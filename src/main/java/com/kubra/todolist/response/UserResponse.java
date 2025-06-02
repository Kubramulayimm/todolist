package com.kubra.todolist.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private boolean active;
}
