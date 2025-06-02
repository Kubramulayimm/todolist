package com.kubra.todolist.mapper;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.model.Category;
import com.kubra.todolist.model.Task;
import com.kubra.todolist.response.TaskResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void testToTaskResponse() {
        Task task = Task.builder()
                .id("t1")
                .userId("u1")
                .title("Test Task")
                .description("Unit test for mapper")
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(1))
                .priority(TaskPriority.HIGH)
                .completed(false)
                .active(true)
                .deleted(false)
                .favorite(true)
                .category(Category.builder().id("c1").name("Work").build())
                .build();

        TaskResponse response = taskMapper.toTaskResponse(task);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo("t1");
        assertThat(response.getTitle()).isEqualTo("Test Task");
        assertThat(response.getUserId()).isEqualTo("u1");
        assertThat(response.getCategory()).isNotNull();
        assertThat(response.getCategory().getId()).isEqualTo("c1");
    }

    @Test
    void testToTaskResponseList() {
        Task task1 = Task.builder().id("1").title("Task 1").userId("u1").build();
        Task task2 = Task.builder().id("2").title("Task 2").userId("u2").build();

        List<TaskResponse> responseList = taskMapper.toTaskResponseList(List.of(task1, task2));

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).getTitle()).isEqualTo("Task 1");
        assertThat(responseList.get(1).getTitle()).isEqualTo("Task 2");
    }
}