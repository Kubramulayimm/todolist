package com.kubra.todolist.repository;

import com.kubra.todolist.model.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;

public interface UserRepository extends CouchbaseRepository<User, String> {

    Optional<User> findByEmail(String email);

}
