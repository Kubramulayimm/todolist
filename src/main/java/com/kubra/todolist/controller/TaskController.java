package com.kubra.todolist.controller;

import com.kubra.todolist.request.TaskRequest;
import com.kubra.todolist.response.TaskResponse;
import com.kubra.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/task")
@Tag(name = "Task API")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.createTask(taskRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<TaskResponse> updateTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(taskService.updateTask(taskRequest));
    }

    @GetMapping("/{taskId}")
    public TaskResponse getTaskById(@NonNull @PathVariable String taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/list/{userId}/category/{categoryId}")
    public List<TaskResponse> getTaskByCategoryId(@PathVariable String categoryId, @PathVariable String userId) {
        return taskService.getTaskByCategoryId(categoryId, userId);
    }

    @GetMapping("/list/{userId}")
    public List<TaskResponse> getTasks(@PathVariable String userId) {
        return taskService.getTasks(userId);
    }

    @GetMapping("/getTasksByPriority/{userId}")
    public List<TaskResponse> getTasksByPriority(@PathVariable String userId) {
        return taskService.getTasksSortedByPriority(userId);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<Void> deleteTaskId(@PathVariable String taskId) {
        taskService.deleteTaskId(taskId);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/complete/{taskId}")
    public ResponseEntity<Void> completeTask(@PathVariable String taskId) {
        taskService.completeTaskId(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/removeFavorite/{taskId}")
    public ResponseEntity<Void> removeFavoriteByTaskId(@PathVariable String taskId, boolean favorite) {
        taskService.favoriteByTaskId(taskId, favorite);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/favorite/{taskId}")
    public ResponseEntity<Void> favoriteByTaskId(@PathVariable String taskId, boolean favorite) {
        taskService.favoriteByTaskId(taskId, favorite);
        return ResponseEntity.ok().build();
    }

}
