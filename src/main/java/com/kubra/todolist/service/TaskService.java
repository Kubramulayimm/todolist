package com.kubra.todolist.service;

import com.kubra.todolist.model.Category;
import com.kubra.todolist.request.TaskRequest;
import com.kubra.todolist.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse updateTask(TaskRequest request);

    TaskResponse getTaskById(String taskId);

    List<TaskResponse> getTaskByCategoryId(String categoryId, String userId);

    List<TaskResponse> getTasksSortedByPriority(String userId);

    List<TaskResponse> getTasks(String userId);

    void deleteTaskId(String taskId);

    void completeTaskId(String taskId);

    void favoriteByTaskId(String taskId, boolean favorite);

    void updateCategoryNameInTasks(Category category);

    void removeCategoryFromTasks(String categoryId);

}
