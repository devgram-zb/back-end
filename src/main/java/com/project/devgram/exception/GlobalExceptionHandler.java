package com.project.devgram.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DevGramException.class)
    public ErrorResponse devGramExceptionHandler(DevGramException e) {
        return ErrorResponse.builder()
            .errorCode(e.getErrorCode())
            .errorMessage(e.getErrorMessage())
            .build();
    }
}
