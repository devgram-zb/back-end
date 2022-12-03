package com.project.devgram.type.status;

import com.fasterxml.jackson.annotation.JsonValue;
import com.project.devgram.type.BaseEnumCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status implements BaseEnumCode<String> {
	NORMAL("N","정상상태"),
	DELETED("D", "삭제상태");

	@JsonValue
	private final String value;
	private final String code;


	@Override
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}
}
