package com.project.devgram.controller;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.dto.CommentResponse.GroupComment;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.CommentService;
import com.project.devgram.type.CommentStatus;
import com.project.devgram.util.pagerequest.CommentPageRequest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    private final TokenService tokenService;

    /*
     * 댓글 등록 api(로그인 필요)
    */
    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto commentInput, HttpServletRequest request) {
        commentInput.setCreatedBy(tokenService.getUsername(request.getHeader("Authentication")));

        return commentService.addComment(commentInput);
    }

    /*
     * 댓글 조회(로그인 불필요)
     */
    @GetMapping
    public List<GroupComment> getCommentList(@RequestParam Long boardSeq, CommentPageRequest commentPageRequest) {
        return commentService.getCommentList(boardSeq, commentPageRequest);
    }

    /*
     * 신고 댓글 조회(관리자 권한 필요)
     */
    @GetMapping("/accuse/admin")
    public List<CommentDto> getAccusedCommentList() {
        return commentService.getAccusedCommentList();
    }

    /*
     * 댓글 삭제(로그인 필요)
     */
    @DeleteMapping
    public String deleteComment(@RequestParam Long commentSeq) {
        return commentService.deleteComment(commentSeq);
    }

    /*
     * 댓글 신고(로그인 필요)
     */
    @PostMapping("/accuse")
    public CommentAccuseDto accuseComment(@RequestBody CommentAccuseDto commentAccuseDto, HttpServletRequest request) {
        commentAccuseDto.setCreatedBy(tokenService.getUsername(request.getHeader("Authentication")));
        return commentService.accuseComment(commentAccuseDto);
    }

    /*
     * 특정 신고 댓글 신고 내역 조회(관리자 권한 필요)
     */
    @GetMapping("/accuse/detail/admin")
    public List<CommentAccuseDto> getAccusedCommentDetail(@RequestParam Long commentSeq) {
        return commentService.getAccusedCommentDetail(commentSeq);
    }

    /*
     * 댓글 상태 업데이트(관리자 권한 필요)
     */
    @PutMapping("/status/admin")
    public String updateCommentStatus(@RequestBody CommentDto commentDto) {
        return commentService.updateCommentStatus(commentDto.getCommentSeq(), commentDto.getCommentStatus());
    }

    /*
     * 댓글 내용 업데이트(로그인 필요)
     */
    @PutMapping("/content")
    public CommentDto updateCommentContent(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        commentDto.setUpdatedBy(tokenService.getUsername(request.getHeader("Authentication")));
        return commentService.updateCommentContent(commentDto);
    }
}
