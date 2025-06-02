package com.kubra.todolist.exception;

public class NoTaskFoundForUserException extends RuntimeException {
    public NoTaskFoundForUserException(String userId) {
        super("No tasks found for user with ID: " + userId);
    }
}
