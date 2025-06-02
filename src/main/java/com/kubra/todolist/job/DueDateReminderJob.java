package com.kubra.todolist.job;

import com.kubra.todolist.model.Task;
import com.kubra.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DueDateReminderJob {
    private final TaskRepository taskRepository;

    @Scheduled(cron = "${scheduler.reminders.due-task-cron}")
    public void remindTasksDueTomorrow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneHourLater = now.plusHours(1);

        List<Task> dueTasks = taskRepository.findAllByDueDateBetweenAndCompletedIsFalse(now, oneHourLater, false, true);


        if (dueTasks.isEmpty()) {
            log.info("✅ No upcoming tasks due within 24 hours.");
            return;
        }
        for (Task task : dueTasks) {
            log.info("🔔 Task '{}' is due by {}. Don't forget!", task.getTitle(), task.getDueDate());
        }
    }
}
