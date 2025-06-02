package com.kubra.todolist.repository;

import com.kubra.todolist.model.Task;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CouchbaseRepository<Task, String> {

    List<Task> findAllByUserId(String userId);

    Optional<Task> findById(String id);

    List<Task> findTaskByCategoryIdAndUserId(String categoryId, String userId);

    List<Task> findAllByCategoryId(String categoryId);

    List<Task> findAllByDueDateBetweenAndCompletedIsFalse(LocalDateTime start, LocalDateTime end, boolean completed, boolean active);

}
