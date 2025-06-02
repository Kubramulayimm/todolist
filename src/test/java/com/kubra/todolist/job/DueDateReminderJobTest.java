package com.kubra.todolist.job;

import com.kubra.todolist.model.Task;
import com.kubra.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class DueDateReminderJobTest {

    private TaskRepository taskRepository;
    private DueDateReminderJob dueDateReminderJob;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        dueDateReminderJob = new DueDateReminderJob(taskRepository);
    }

    @Test
    void testRemindTasksDueTomorrow_whenTasksExist() {
        Task task = Task.builder()
                .id("1")
                .title("Finish Report")
                .dueDate(LocalDateTime.now().plusMinutes(30))
                .completed(false)
                .deleted(false)
                .active(true)
                .build();

        when(taskRepository.findAllByDueDateBetweenAndCompletedIsFalse(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false),
                eq(true))
        ).thenReturn(List.of(task));

        dueDateReminderJob.remindTasksDueTomorrow();

        verify(taskRepository, times(1)).findAllByDueDateBetweenAndCompletedIsFalse(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false),
                eq(true)
        );
    }

    @Test
    void testRemindTasksDueTomorrow_whenNoTasks() {
        when(taskRepository.findAllByDueDateBetweenAndCompletedIsFalse(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false),
                eq(true))
        ).thenReturn(Collections.emptyList());

        dueDateReminderJob.remindTasksDueTomorrow();

        verify(taskRepository, times(1)).findAllByDueDateBetweenAndCompletedIsFalse(
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                eq(false),
                eq(true)
        );
    }
}