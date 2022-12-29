package com.project.devgram.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatus {
    POST("등록 상태"),
    ACCUSE("신고 상태"),
    DELETE("삭제 상태");

    private final String description;

    @JsonCreator
    public static CommentStatus from(String value) {
        for (CommentStatus commentStatus: CommentStatus.values()) {
            if (commentStatus.name().equals(value)) {
                return commentStatus;
            }
        }
        return null;
    }
}
