package com.kubra.todolist.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCategoryNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String userId = "user123";
        String categoryName = "Work";

        UserCategoryNotFoundException exception =
                new UserCategoryNotFoundException(userId, categoryName);

        String expectedMessage = "User with ID user123 does not have category: Work";
        assertEquals(expectedMessage, exception.getMessage());
    }
}