package com.project.devgram.controller;

import com.project.devgram.dto.RegisterBoard;
import com.project.devgram.dto.SearchBoard;
import com.project.devgram.dto.UpdateBoard;
import com.project.devgram.service.BoardService;
import com.project.devgram.service.TagService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final TagService tagService;

    @PostMapping
    public RegisterBoard.Response registerBoard(@RequestBody @Valid RegisterBoard.Request request) {

        tagService.addTag(request.getTagNames());

        return RegisterBoard
            .Response
            .from(boardService
                .registerBoard(request.getTitle(), request.getContent()), request.getTagNames());
    }

    @GetMapping
    public List<SearchBoard.Response> searchBoards(@ModelAttribute SearchBoard.Request request) {
        return SearchBoard.Response.listOf(boardService.searchBoards(request));
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
