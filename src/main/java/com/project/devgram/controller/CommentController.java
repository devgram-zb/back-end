package com.project.devgram.controller;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.dto.CommentResponse.GroupComment;
import com.project.devgram.service.CommentService;
import com.project.devgram.type.CommentStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {

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
    public List<GroupComment> getCommentList(@RequestParam Long boardSeq) {
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
    public String deleteComment(@RequestParam Long commentSeq) {
        return commentService.deleteComment(commentSeq);
    }

    /*
     * 댓글 신고
     */
    @PostMapping("/accuse")
    public CommentAccuseDto accuseComment(@RequestBody CommentAccuseDto commentAccuseDto) {
        return commentService.accuseComment(commentAccuseDto);
    }

    /*
     * 특정 신고 댓글 신고 내역 조회
     */
    @GetMapping("/accuse/detail")
    public List<CommentAccuseDto> getAccusedCommentDetail(@RequestParam Long commentSeq) {
        return commentService.getAccusedCommentDetail(commentSeq);
    }

    /*
     * 댓글 상태 업데이트(관리자)
     */
    @PutMapping
    public CommentDto updateCommentStatus(@RequestParam Long commentSeq, @RequestParam
        CommentStatus commentStatus) {
        return commentService.updateCommentStatus(commentSeq, commentStatus);
    }
}
