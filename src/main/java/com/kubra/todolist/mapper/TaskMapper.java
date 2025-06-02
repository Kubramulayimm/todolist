package com.kubra.todolist.mapper;

import com.kubra.todolist.model.Task;
import com.kubra.todolist.response.TaskResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponse toTaskResponse(Task task);

    List<TaskResponse> toTaskResponseList(List<Task> tasks);

}
