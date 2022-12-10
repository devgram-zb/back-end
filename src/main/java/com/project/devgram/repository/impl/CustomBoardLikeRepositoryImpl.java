package com.project.devgram.repository.impl;

import com.project.devgram.dto.BoardLikeDto;
import com.project.devgram.dto.SearchBoardLike.Request;
import com.project.devgram.repository.CustomBoardLikeRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.project.devgram.entity.QBoardLike.boardLike;

@RequiredArgsConstructor
public class CustomBoardLikeRepositoryImpl implements CustomBoardLikeRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardLikeDto> findBy(Request request) {
		return queryFactory.select(Projections.fields(BoardLikeDto.class,
				boardLike.boardLikeSeq,
				boardLike.user.userSeq,
				boardLike.user.username.as("userName"),
				boardLike.board.boardSeq,
				boardLike.board.title.as("boardTitle")))
			.from(boardLike)
			.where(isBoardSeqSame(request.getBoardSeq()), isUserSeqSame(request.getUserSeq()))
			.fetch();
	}

	private BooleanExpression isBoardSeqSame(Long boardSeq) {
		if (boardSeq != null) {
			return boardLike.board.boardSeq.eq(boardSeq);
		}
		return null;
	}

	private BooleanExpression isUserSeqSame(Long userSeq) {
		if (userSeq != null) {
			return boardLike.user.userSeq.eq(userSeq);
		}
		return null;
	}

}
