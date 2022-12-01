package com.project.devgram.exception;

import com.project.devgram.exception.errorcode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DevGramException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public DevGramException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
