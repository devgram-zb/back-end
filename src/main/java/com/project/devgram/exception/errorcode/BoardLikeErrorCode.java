package com.project.devgram.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardLikeErrorCode implements ErrorCode {
	ALREADY_LIKED_BOARD("이미 좋아요를 하셨습니다."),
	LIKE_NOT_EXIST("햬당 좋아요는 존재하지 않습니다.");
	private final String description;
}
