package com.kubra.todolist.repository;

import com.kubra.todolist.model.Category;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CouchbaseRepository<Category, String> {

    List<Category> findAllByUserId(String userId);

    Optional<Category> findById(String id);

}
