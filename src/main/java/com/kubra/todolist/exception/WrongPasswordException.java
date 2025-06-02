package com.kubra.todolist.exception;

public class WrongPasswordException extends RuntimeException {
    private static final String MESSAGE = "Wrong password.";

    public WrongPasswordException() {
        super(MESSAGE);
    }
}
