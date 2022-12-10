package com.project.devgram.service;

import com.project.devgram.dto.BoardLikeDto;
import com.project.devgram.dto.SearchBoardLike.Request;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardLike;
import com.project.devgram.entity.User;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardLikeErrorCode;
import com.project.devgram.repository.BoardLikeRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

	private final BoardLikeRepository boardLikeRepository;
	private final UserRepository userRepository;
	private final BoardService boardService;

	public List<BoardLikeDto> searchBoardLike(Request request) {
		return boardLikeRepository.findBy(request);
	}

	@Transactional
	public BoardLikeDto registerBoardLike(Long boardSeq, Long userSeq) {
		boardLikeRepository.findByBoard_BoardSeqAndUser_UserSeq(boardSeq, userSeq).ifPresent(boardLike -> {
			throw new DevGramException(BoardLikeErrorCode.ALREADY_LIKED_BOARD);
		});

		Board board = boardService.getBoard(boardSeq);

		User user = userRepository.findById(userSeq).orElseThrow(() -> new UsernameNotFoundException("asdasdd"));

		board.increaseLikeCount();

		return BoardLikeDto.from(boardLikeRepository.save(BoardLikeDto.toEntity(board, user)));

	}

	@Transactional
	public void deleteBoardLike(Long boardLikeSeq) {
		BoardLike boardLike = boardLikeRepository.findById(boardLikeSeq).orElseThrow(() -> new DevGramException(BoardLikeErrorCode.LIKE_NOT_EXIST));

		boardLike.getBoard().decreaseLikeCount();

		boardLikeRepository.delete(boardLike);
	}
}
