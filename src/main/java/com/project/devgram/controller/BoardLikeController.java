package com.project.devgram.controller;


import com.project.devgram.dto.DeleteBoardLike;
import com.project.devgram.dto.RegisterBoardLike;
import com.project.devgram.dto.SearchBoardAccuse;
import com.project.devgram.dto.SearchBoardLike;
import com.project.devgram.service.BoardLikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/like")
@RequiredArgsConstructor
public class BoardLikeController {

	private final BoardLikeService boardLikeService;

	@GetMapping
	public List<SearchBoardLike.Response> searchBoardLike(SearchBoardLike.Request request) {
		return SearchBoardLike.Response.listOf(boardLikeService.searchBoardLike(request));
	}

	@PostMapping
	public RegisterBoardLike.Response registerBoardLike(@RequestBody RegisterBoardLike.Request request) {
		return RegisterBoardLike.Response.of(boardLikeService.registerBoardLike(request.getBoardSeq(), request.getUserSeq()));
	}

	@DeleteMapping("{boardLikeSeq}")
	public void deleteBoardLike(@PathVariable Long boardLikeSeq) {
		boardLikeService.deleteBoardLike(boardLikeSeq);
	}

}
