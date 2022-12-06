package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TagErrorCode implements ErrorCode {
    NOT_EXISTENT_TAG("등록된 태그가 없습니다.");

    private final String description;
}