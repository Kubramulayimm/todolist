package com.kubra.todolist.config;

import com.kubra.todolist.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handle_whenExceptionThrown_returnsInternalServerErrorResponse() {
        // given
        Exception ex = new RuntimeException("Something went wrong");

        // when
        ResponseEntity<ExceptionResponse> responseEntity = exceptionHandler.handle(ex);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ExceptionResponse response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("500", response.getExceptCode());
        assertTrue(response.getExceptMessage().contains("Something went wrong"));
        assertNotNull(response.getOccurredAt());
        assertTrue(response.getOccurredAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    }
}