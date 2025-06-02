package com.kubra.todolist.exception;

public class CategoryUpdateFailedException extends RuntimeException {

    private static final String MESSAGE = "Category update failed.";

    public CategoryUpdateFailedException() {
        super(MESSAGE);
    }
}
