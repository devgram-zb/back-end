package com.project.devgram.service;

import com.project.devgram.entity.Board;
import com.project.devgram.dto.BoardDto;
import com.project.devgram.repository.BoardRepository;
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
}
