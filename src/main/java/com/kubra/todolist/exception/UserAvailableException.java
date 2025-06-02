package com.kubra.todolist.exception;

public class UserAvailableException extends RuntimeException {
    private static final String MESSAGE = "User is available";

    public UserAvailableException() {
        super(MESSAGE);
    }
}
