package com.kubra.todolist.repository;

import com.kubra.todolist.enums.TaskPriority;
import com.kubra.todolist.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.couchbase.DataCouchbaseTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataCouchbaseTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveAndFindById() {
        // given
        Task task = Task.builder()
                .id("task-test-id")
                .userId("user-1")
                .title("Test Task")
                .description("Description")
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDateTime.now().plusDays(1))
                .completed(false)
                .active(true)
                .build();

        taskRepository.save(task);

        // when
        Optional<Task> found = taskRepository.findById("task-test-id");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Task");
    }

    @Test
    void testFindAllByUserId() {
        // given
        Task task1 = Task.builder().id("t1").userId("u1").title("A").build();
        Task task2 = Task.builder().id("t2").userId("u1").title("B").build();
        Task task3 = Task.builder().id("t3").userId("u2").title("C").build();

        taskRepository.saveAll(List.of(task1, task2, task3));

        // when
        List<Task> tasks = taskRepository.findAllByUserId("u1");

        // then
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getTitle).containsExactlyInAnyOrder("A", "B");
    }

    @Test
    void testFindByDueDateAndCompletedFalse() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(3);

        Task task1 = Task.builder()
                .id("due1")
                .dueDate(now.plusMinutes(10))
                .completed(false)
                .active(true)
                .build();

        Task task2 = Task.builder()
                .id("due2")
                .dueDate(now.plusMinutes(90))
                .completed(false)
                .active(true)
                .build();

        Task task3 = Task.builder()
                .id("done")
                .dueDate(now.plusMinutes(30))
                .completed(true)
                .active(true)
                .build();

        taskRepository.saveAll(List.of(task1, task2, task3));

        // when
        List<Task> dueSoon = taskRepository.findAllByDueDateBetweenAndCompletedIsFalse(now, later, false, true);

        // then
        assertThat(dueSoon).hasSize(2);
        assertThat(dueSoon).extracting(Task::getId).contains("due1", "due2");
    }
}