package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardAccuseErrorCode implements ErrorCode {
	ALREADY_ACCUSED_BOARD("작성자가 이미 신고한 게시글입니다.");
	private final String description;
}
