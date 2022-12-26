package com.project.devgram.controller;

import com.project.devgram.dto.RegisterBoard;
import com.project.devgram.dto.SearchBoard.Response;
import com.project.devgram.dto.UpdateBoard;
import com.project.devgram.dto.DetailResponse;
import com.project.devgram.service.BoardService;
import com.project.devgram.service.BoardServiceContainer;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	private final BoardServiceContainer boardServiceContainer;

	@PostMapping
	public RegisterBoard.Response registerBoard(@RequestBody @Valid RegisterBoard.Request request) {
		return boardServiceContainer.registerBoard(request);
	}

	@GetMapping
	public Page<Response> searchBoards(@PageableDefault(page = 0, size = 5) Pageable pageable, String sort,
		@RequestParam(required = false) List<Long> tagSeqList) {
		return boardService.searchBoards(pageable, sort, tagSeqList);
	}

	@GetMapping("/{boardSeq}")
	public DetailResponse detailBoards(@PathVariable Long boardSeq) {
		return boardService.searchBoardDetail(boardSeq);
	}

	@GetMapping("/follow")
	public Page<Response> searchFollowingBoards(@PageableDefault(page = 0, size = 5) Pageable pageable,
		@RequestParam(required = false) List<Long> tagSeqList) {
		return boardService.searchFollowingBoards(pageable, tagSeqList);
	}

	@PutMapping
	public UpdateBoard.Response updateBoard(@RequestBody UpdateBoard.Request request) {
		return UpdateBoard.Response.of(boardService.updateBoard(request));
	}

	@DeleteMapping("/{boardSeq}")
	public void deleteBoard(@PathVariable Long boardSeq) {
		boardService.deleteBoard(boardSeq);
	}

}
