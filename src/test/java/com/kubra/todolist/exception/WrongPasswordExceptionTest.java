package com.kubra.todolist.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WrongPasswordExceptionTest {

    @Test
    void testExceptionMessage() {
        WrongPasswordException exception = new WrongPasswordException();

        String expectedMessage = "Wrong password.";
        assertEquals(expectedMessage, exception.getMessage());
    }
}