package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardErrorCode implements ErrorCode {
	CANNOT_FIND_BOARD_BY_BOARDSEQ("boardSeq에 해당하는 Board를 찾을 수 없습니다."),
	LIKE_COUNT_CANNOT_BE_MINUS("좋아요 개수는 음수가 될수 없습니다."),
	ALREADY_DELETED_BOARD("이미 삭제된 보드 입니다.");
	private final String description;
}

