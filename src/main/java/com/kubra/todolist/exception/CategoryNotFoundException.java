package com.kubra.todolist.exception;

public class CategoryNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Category is not found.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
