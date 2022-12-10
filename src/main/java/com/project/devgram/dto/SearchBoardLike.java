package com.project.devgram.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchBoardLike {

	@Getter
	public static class Request {

		private Long boardSeq;
		private Long userSeq;

	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private Long boardLikeSeq;
		private Long userSeq;
		private String userName;
		private Long boardSeq;
		private String boardTitle;

		public static Response of(BoardLikeDto boardLikeDto) {
			return Response.builder()
				.boardLikeSeq(boardLikeDto.getBoardLikeSeq())
				.userSeq(boardLikeDto.getUserSeq())
				.userName(boardLikeDto.getUserName())
				.boardSeq(boardLikeDto.getBoardSeq())
				.boardTitle(boardLikeDto.getBoardTitle())
				.build();
		}

		public static List<Response> listOf(List<BoardLikeDto> boardLikeDtoList) {
			return boardLikeDtoList.stream()
				.map(Response::of)
				.collect(Collectors.toList());
		}
	}
}
