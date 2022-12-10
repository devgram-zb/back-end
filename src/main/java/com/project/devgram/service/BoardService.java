package com.project.devgram.service;

import com.project.devgram.dto.SearchBoard.Request;
import com.project.devgram.dto.UpdateBoard;
import com.project.devgram.entity.Board;
import com.project.devgram.dto.BoardDto;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardErrorCode;
import com.project.devgram.repository.BoardRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardDto registerBoard(String title, String content) {
		return BoardDto.fromEntity(boardRepository
			.save(Board
				.builder()
				.content(content)
				.title(title)
				.build()));
	}

	public List<BoardDto> searchBoards(Request request) {
		return boardRepository.findBy(request);
	}

	@Transactional
	public BoardDto updateBoard(UpdateBoard.Request request) {
		Board board = getBoard(request.getBoardSeq());

		board.updateTitle(request.getTitle());
		board.updateContent(request.getContent());

		return BoardDto.fromEntity(board);
	}

	@Transactional
	public void deleteBoard(Long boardSeq) {
		Board board = getBoard(boardSeq);
		board.deleteBoard();
	}

	public Board getBoard(Long boardSeq) {
		return boardRepository.findById(boardSeq).orElseThrow(() ->
			new DevGramException(BoardErrorCode.CANNOT_FIND_BOARD_BY_BOARDSEQ));
	}


}
