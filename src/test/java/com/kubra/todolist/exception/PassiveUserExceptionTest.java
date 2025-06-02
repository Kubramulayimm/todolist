package com.kubra.todolist.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassiveUserExceptionTest {

    @Test
    void testExceptionMessage() {
        PassiveUserException exception = new PassiveUserException();

        assertEquals("User is passive.", exception.getMessage());
    }
}