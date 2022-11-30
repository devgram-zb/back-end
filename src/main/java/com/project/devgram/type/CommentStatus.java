package com.project.devgram.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus {
    POST("등록 상태"),
    ACCUSE("신고 상태"),
    DELETE("삭제 상태");

    private final String description;
}
