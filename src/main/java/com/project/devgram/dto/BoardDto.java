package com.project.devgram.dto;

import com.project.devgram.entity.Board;
import com.project.devgram.type.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private String title;
	private String content;
	private Status status;
	private Integer likeCount;

	public static BoardDto fromEntity(Board board){
		return BoardDto.builder()
			.title(board.getTitle())
			.content(board.getContent())
			.status(board.getStatus())
			.likeCount(board.getLikeCount())
			.build();
	}
}
