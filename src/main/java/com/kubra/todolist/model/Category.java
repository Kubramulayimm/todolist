package com.kubra.todolist.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Category {
    @Id
    private String id;
    private String name;
    private String userId;
    private boolean active;
    private LocalDateTime deletedAt;
    private boolean deleted;

}
