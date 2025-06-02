package com.kubra.todolist.service.impl;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.exception.*;
import com.kubra.todolist.mapper.TaskMapper;
import com.kubra.todolist.model.Category;
import com.kubra.todolist.model.Task;
import com.kubra.todolist.repository.TaskRepository;
import com.kubra.todolist.request.TaskRequest;
import com.kubra.todolist.response.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskRequest taskRequest;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        openMocks(this);

        taskRequest = TaskRequest.builder()
                .userId("user-id")
                .title("Test Title")
                .description("Test Description")
                .dueDate(LocalDateTime.now().plusDays(1))
                .priority(TaskPriority.HIGH)
                .build();

        task = Task.builder()
                .id(UUID.randomUUID().toString())
                .userId(taskRequest.getUserId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .priority(taskRequest.getPriority())
                .createdAt(LocalDateTime.now())
                .completed(false)
                .deleted(false)
                .active(true)
                .build();

        taskResponse = TaskResponse.builder()
                .id(task.getId())
                .userId(task.getUserId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .priority(task.getPriority())
                .build();
    }

    @Test
    void testCreateTask_shouldReturnTaskResponse() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toTaskResponse(any(Task.class))).thenReturn(taskResponse);

        TaskResponse response = taskService.createTask(taskRequest);

        assertEquals(taskResponse, response);
    }

    @Test
    void testGetTaskById_shouldReturnTask() {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse result = taskService.getTaskById("task-id");

        assertEquals(taskResponse, result);
    }

    @Test
    void testGetTaskById_shouldThrowException() {
        when(taskRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById("invalid-id"));
    }

    @Test
    void testDeleteTaskId_shouldSetFlagsAndSave() {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));

        taskService.deleteTaskId("task-id");

        assertTrue(task.isDeleted());
        assertFalse(task.isActive());
        verify(taskRepository).save(task);
    }

    @Test
    void testCompleteTask_shouldMarkCompleted() {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));

        taskService.completeTaskId("task-id");

        assertTrue(task.isCompleted());
        verify(taskRepository).save(task);
    }

    @Test
    void testFavoriteTask_shouldSetFavorite() {
        when(taskRepository.findById(anyString())).thenReturn(Optional.of(task));

        taskService.favoriteByTaskId("task-id", true);

        assertTrue(task.isFavorite());
        verify(taskRepository).save(task);
    }

    @Test
    void testGetTasks_shouldReturnList() {
        List<Task> tasks = List.of(task);
        when(taskRepository.findAllByUserId("user-id")).thenReturn(tasks);
        when(taskMapper.toTaskResponseList(tasks)).thenReturn(List.of(taskResponse));

        List<TaskResponse> responses = taskService.getTasks("user-id");

        assertEquals(1, responses.size());
    }

    @Test
    void testUpdateTask_shouldApplyChanges() {
        TaskRequest updateRequest = TaskRequest.builder()
                .id(task.getId())
                .title("Updated Title")
                .description("Updated Desc")
                .priority(TaskPriority.MEDIUM)
                .build();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse response = taskService.updateTask(updateRequest);

        assertEquals(taskResponse, response);
    }

    @Test
    void testRemoveCategoryFromTasks_shouldNullCategory() {
        task.setCategory(Category.builder().id("cat-id").name("Category").build());
        when(taskRepository.findAllByCategoryId("cat-id")).thenReturn(List.of(task));

        taskService.removeCategoryFromTasks("cat-id");

        assertNull(task.getCategory());
        verify(taskRepository).saveAll(List.of(task));
    }

    @Test
    void testUpdateCategoryNameInTasks_shouldUpdateName() {
        Category category = Category.builder().id("cat-id").name("Updated Name").build();
        task.setCategory(Category.builder().id("cat-id").name("Old Name").build());
        when(taskRepository.findAllByCategoryId("cat-id")).thenReturn(List.of(task));

        taskService.updateCategoryNameInTasks(category);

        assertEquals("Updated Name", task.getCategory().getName());
        verify(taskRepository).saveAll(List.of(task));
    }
}