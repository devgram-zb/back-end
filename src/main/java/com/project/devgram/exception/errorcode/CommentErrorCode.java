package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode {
    NOT_EXISTENT_COMMENT("존재하지 않는 댓글 입니다."),
    NOT_EXISTENT_COMMENT_FOR_BOARD("해당 게시글에 댓글이 존재하지 않습니다."),
    NOT_EXISTENT_ACCUSED_COMMENT("신고된 댓글이 존재하지 않습니다."),
    ALREADY_DELETED_COMMENT("이미 삭제된 댓글입니다."),
    NOT_EXISTENT_ACCUSE_HISTORY("신고 내역이 존재하지 않습니다.");

    private final String description;
}