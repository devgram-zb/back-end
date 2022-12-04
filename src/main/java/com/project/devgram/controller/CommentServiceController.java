package com.project.devgram.controller;

import com.project.devgram.dto.CommentDto;
import com.project.devgram.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /*
     * 댓글 조회(보드)
     */
    @GetMapping
    public List<CommentDto> getCommentList(@RequestParam Long boardSeq) {
        return commentService.getCommentList(boardSeq);
    }

    /*
     * 신고 댓글 조회(관리자)
     */
    @GetMapping("/accuse")
    public List<CommentDto> getAccusedCommentList() {
        return commentService.getAccusedCommentList();
    }

    /*
     * 댓글 삭제
     */
    @DeleteMapping
    public CommentDto deleteComment(@RequestParam Long commentSeq) {
        return commentService.deleteComment(commentSeq);
    }
}
