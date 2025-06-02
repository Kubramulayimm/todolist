package com.kubra.todolist.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String exceptCode;
    private final String exceptMessage;
    private final LocalDateTime occurredAt;

}
