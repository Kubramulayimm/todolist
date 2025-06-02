package com.kubra.todolist.controller;

import org.junit.jupiter.api.Test;
import com.kubra.todolist.request.TaskRequest;
import com.kubra.todolist.response.TaskResponse;
import com.kubra.todolist.service.TaskService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        taskRequest = TaskRequest.builder()
                .id("ec486951-3193-4ab8-8cc9-8b77a185fb7f")
                .userId("8992e641-996f-485c-b181-7a844e523439")
                .title("Test Task")
                .description("A test task")
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        taskResponse = TaskResponse.builder()
                .id(taskRequest.getId())
                .userId(taskRequest.getUserId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .build();
    }

    @Test
    void testCreateTask() {
        when(taskService.createTask(taskRequest)).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> response = taskController.createTask(taskRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(taskResponse, response.getBody());
        verify(taskService).createTask(taskRequest);
    }

    @Test
    void testUpdateTask() {
        when(taskService.updateTask(taskRequest)).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> response = taskController.updateTask(taskRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(taskResponse, response.getBody());
        verify(taskService).updateTask(taskRequest);
    }

    @Test
    void testGetTaskById() {
        when(taskService.getTaskById(taskRequest.getId())).thenReturn(taskResponse);

        TaskResponse response = taskController.getTaskById(taskRequest.getId());

        assertEquals(taskResponse, response);
        verify(taskService).getTaskById(taskRequest.getId());
    }

    @Test
    void testGetTasksByCategoryId() {
        when(taskService.getTaskByCategoryId("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "8992e641-996f-485c-b181-7a844e523439")).thenReturn(Collections.singletonList(taskResponse));

        List<TaskResponse> responses = taskController.getTaskByCategoryId("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "8992e641-996f-485c-b181-7a844e523439");

        assertEquals(1, responses.size());
        verify(taskService).getTaskByCategoryId("ec486951-3193-4ab8-8cc9-8b77a185fb7f", "8992e641-996f-485c-b181-7a844e523439");
    }

    @Test
    void testGetTasks() {
        when(taskService.getTasks("8992e641-996f-485c-b181-7a844e523439")).thenReturn(Collections.singletonList(taskResponse));

        List<TaskResponse> responses = taskController.getTasks("8992e641-996f-485c-b181-7a844e523439");

        assertEquals(1, responses.size());
        verify(taskService).getTasks("8992e641-996f-485c-b181-7a844e523439");
    }

    @Test
    void testGetTasksByPriority() {
        when(taskService.getTasksSortedByPriority("8992e641-996f-485c-b181-7a844e523439")).thenReturn(Collections.singletonList(taskResponse));

        List<TaskResponse> responses = taskController.getTasksByPriority("8992e641-996f-485c-b181-7a844e523439");

        assertEquals(1, responses.size());
        verify(taskService).getTasksSortedByPriority("8992e641-996f-485c-b181-7a844e523439");
    }

    @Test
    void testDeleteTaskId() {
        ResponseEntity<Void> response = taskController.deleteTaskId(taskRequest.getId());

        assertEquals(200, response.getStatusCode().value());
        verify(taskService).deleteTaskId(taskRequest.getId());
    }

    @Test
    void testCompleteTask() {
        ResponseEntity<Void> response = taskController.completeTask(taskRequest.getId());

        assertEquals(200, response.getStatusCode().value());
        verify(taskService).completeTaskId(taskRequest.getId());
    }

    @Test
    void testFavoriteTask() {
        ResponseEntity<Void> response = taskController.favoriteByTaskId(taskRequest.getId(), true);

        assertEquals(200, response.getStatusCode().value());
        verify(taskService).favoriteByTaskId(taskRequest.getId(), true);
    }

    @Test
    void testRemoveFavoriteTask() {
        ResponseEntity<Void> response = taskController.removeFavoriteByTaskId(taskRequest.getId(), false);

        assertEquals(200, response.getStatusCode().value());
        verify(taskService).favoriteByTaskId(taskRequest.getId(), false);
    }
}