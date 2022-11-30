package com.project.devgram.controller;

import com.project.devgram.dto.CommentDto;
import com.project.devgram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentServiceController {

    private final CommentService commentService;

    /*
     * 댓글 등록 api
    */
    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto commentInput) {
        return commentService.addComment(commentInput);
    }
}
