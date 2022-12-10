package com.project.devgram.dto;

import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardLike;
import com.project.devgram.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLikeDto {

	private Long boardLikeSeq;
	private Long userSeq;
	private String userName;
	private Long boardSeq;
	private String boardTitle;

	public static BoardLike toEntity(Board board, User user) {
		return BoardLike.builder()
			.board(board)
			.user(user)
			.build();
	}

	public static BoardLikeDto from(BoardLike boardLike) {
		return BoardLikeDto.builder()
			.boardLikeSeq(boardLike.getBoardLikeSeq())
			.userSeq(boardLike.getUser().getUserSeq())
			.userName(boardLike.getUser().getUsername())
			.boardSeq(boardLike.getBoard().getBoardSeq())
			.boardTitle(boardLike.getBoard().getTitle())
			.build();
	}
}
