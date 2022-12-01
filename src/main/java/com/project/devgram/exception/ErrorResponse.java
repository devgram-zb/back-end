package com.project.devgram.exception;

import com.project.devgram.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
