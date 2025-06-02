package com.kubra.todolist.exception;

public class PassiveUserException extends RuntimeException {

    private static final String MESSAGE = "User is passive.";

    public PassiveUserException() {
        super(MESSAGE);
    }
}
