package com.kubra.todolist.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    private String id;
    @NotBlank(message = "Name cannot empty")
    private String name;
    @NotBlank(message = "User ID cannot be null")
    private String userId;
    private boolean active;

}
