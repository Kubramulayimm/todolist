package com.kubra.todolist.model;

import com.kubra.todolist.enums.TaskPriority;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

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

    private boolean active;

    private LocalDateTime deletedAt;

    private boolean deleted;

    private boolean favorite;

    private Category category;

}
