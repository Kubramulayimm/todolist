package com.kubra.todolist.service.impl;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.exception.*;
import com.kubra.todolist.mapper.TaskMapper;
import com.kubra.todolist.model.Category;
import com.kubra.todolist.model.Task;
import com.kubra.todolist.repository.TaskRepository;
import com.kubra.todolist.request.TaskRequest;
import com.kubra.todolist.response.TaskResponse;
import com.kubra.todolist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskResponse createTask(TaskRequest taskRequest) {

        Task newTask = Task.builder()
                .id(UUID.randomUUID()
                        .toString())
                .userId(taskRequest.getUserId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .dueDate(taskRequest.getDueDate())
                .priority(taskRequest.getPriority() == null ?
                        TaskPriority.LOW :
                        taskRequest.getPriority())
                .completed(false)
                .deleted(false)
                .active(true)
                .favorite(taskRequest.isFavorite())
                .category(taskRequest.getCategory())
                .build();

        Task taskSave = taskRepository.save(newTask);
        return taskMapper.toTaskResponse(taskSave);
    }

    public TaskResponse updateTask(TaskRequest request) {
        if (request.getId() == null) {
            throw new TaskNotFoundException();
        }

        Task task = taskRepository.findById(request.getId())
                .orElseThrow(TaskNotFoundException::new);


        if (request.getTitle() != null && !request.getTitle().isBlank())
            task.setTitle(request.getTitle());
        if (nonNull(request.getDescription()))
            task.setDescription(request.getDescription());
        if (nonNull(request.getDueDate()))
            task.setDueDate(request.getDueDate());
        if (nonNull(request.getPriority()))
            task.setPriority(request.getPriority());

        if (request.getCategory() != null && request.getCategory().getId() != null)
            task.setCategory(request.getCategory());

        task.setUpdatedAt(LocalDateTime.now());

        Task taskUpdate = taskRepository.save(task);
        return taskMapper.toTaskResponse(taskUpdate);

    }

    public TaskResponse getTaskById(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        return taskMapper.toTaskResponse(task);

    }

    public List<TaskResponse> getTaskByCategoryId(String categoryId, String userId) {
        List<Task> taskByCategoryIdAndUserId = taskRepository.findTaskByCategoryIdAndUserId(categoryId, userId);

        if (taskByCategoryIdAndUserId.isEmpty()) {
            throw new UserCategoryNotFoundException(userId, categoryId);
        }
        return taskMapper.toTaskResponseList(taskByCategoryIdAndUserId);

    }

    public List<TaskResponse> getTasksSortedByPriority(String userId) {
        List<Task> taskList = taskRepository.findAllByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(task -> {
                    TaskPriority p = task.getPriority() == null ?
                            TaskPriority.LOW :
                            task.getPriority();
                    return p.ordinal();
                }))
                .collect(Collectors.toList());
        if (taskList.isEmpty()) {
            throw new NoTaskFoundForUserException(userId);
        }
        return taskMapper.toTaskResponseList(taskList);
    }

    public List<TaskResponse> getTasks(String userId) {
        List<Task> taskList = taskRepository.findAllByUserId(userId);

        if (taskList.isEmpty()) {
            throw new NoTaskFoundForUserException(userId);
        }
        return taskMapper.toTaskResponseList(taskList);

    }

    public void deleteTaskId(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        task.setDeleted(true);
        task.setActive(false);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void completeTaskId(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElse(null);

        if (task == null) {
            throw new TaskNotFoundException();
        }
        task.setCompleted(true);
        taskRepository.save(task);
    }

    public void favoriteByTaskId(String taskId, boolean favorite) {
        Task task = taskRepository.findById(taskId)
                .orElse(null);

        if (task == null) {
            throw new TaskNotFoundException();
        }
        task.setFavorite(favorite);
        taskRepository.save(task);
    }

    public void updateCategoryNameInTasks(Category category) {
        List<Task> tasks = taskRepository.findAllByCategoryId(category.getId());

        for (Task task : tasks) {
            if (task.getCategory() != null) {
                task.getCategory().setName(category.getName());
            }
        }

        taskRepository.saveAll(tasks);
    }

    public void removeCategoryFromTasks(String categoryId) {
        List<Task> tasks = taskRepository.findAllByCategoryId(categoryId);

        for (Task task : tasks) {
            task.setCategory(null);
        }

        taskRepository.saveAll(tasks);
    }

}
