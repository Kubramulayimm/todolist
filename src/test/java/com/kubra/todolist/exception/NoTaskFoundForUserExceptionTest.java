package com.kubra.todolist.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoTaskFoundForUserExceptionTest {

    @Test
    void testExceptionMessage() {
        String userId = "user123";
        NoTaskFoundForUserException exception = new NoTaskFoundForUserException(userId);

        assertEquals("No tasks found for user with ID: user123", exception.getMessage());
    }
}