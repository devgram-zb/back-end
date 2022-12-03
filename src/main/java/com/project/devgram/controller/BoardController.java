package com.project.devgram.controller;

import com.project.devgram.dto.RegisterBoard;
import com.project.devgram.service.BoardService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public RegisterBoard.Response registerBoard(@RequestBody @Valid RegisterBoard.Request request) {
		return RegisterBoard
			.Response
			.from(boardService
				.registerBoard(request.getTitle(), request.getContent()));
	}
}
