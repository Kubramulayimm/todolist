package com.kubra.todolist.response;


import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    @Id
    private String id;
    private String name;
    private String userId;
    private boolean active;
}
