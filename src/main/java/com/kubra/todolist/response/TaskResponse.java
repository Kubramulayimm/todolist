package com.kubra.todolist.response;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.model.Category;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    @Id
    private String id;

    private String userId;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private TaskPriority priority;

    private boolean completed;

    private LocalDateTime deletedAt;

    private boolean deleted;

    private boolean active;

    private boolean favorite;

    private Category category;

}
