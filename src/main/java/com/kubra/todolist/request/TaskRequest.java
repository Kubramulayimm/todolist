package com.kubra.todolist.request;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequest {

    private String id;

    @NotNull(message = "User ID cannot be null")
    private String userId;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private TaskPriority priority;

    private boolean completed;

    private LocalDateTime deletedAt;

    private boolean deleted;

    private boolean favorite;

    private boolean active;

    private Category category;


}
