package com.kubra.todolist.exception;

public class UserCategoryNotFoundException extends RuntimeException {
    public UserCategoryNotFoundException(String userId, String categoryName) {
        super("User with ID " + userId + " does not have category: " + categoryName);
    }
}
