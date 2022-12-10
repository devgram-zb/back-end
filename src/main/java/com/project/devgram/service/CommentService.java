package com.project.devgram.service;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.entity.Comment;
import com.project.devgram.entity.CommentAccuse;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.CommentErrorCode;
import com.project.devgram.repository.CommentAccuseRepository;
import com.project.devgram.repository.CommentRepository;
import com.project.devgram.type.CommentStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentAccuseRepository commentAccuseRepository;
    public static final Sort sortByCreatedAtDesc = Sort.by(Direction.DESC, "createdAt");

    /*
     * 댓글 등록
     */
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
            .content(commentDto.getContent())
            .parentCommentSeq(commentDto.getParentCommentSeq())
            .boardSeq(commentDto.getBoardSeq())
            .createdBy(commentDto.getCreatedBy())
            .commentStatus(CommentStatus.POST)
            .build();

        return CommentDto.from(commentRepository.save(comment));
    }

    /*
     * 댓글 조회(보드)
     */
    public List<CommentDto> getCommentList(Long boardSeq) {

        List<Comment> commentList = commentRepository.findByBoardSeqAndCommentStatusNot(boardSeq,
                CommentStatus.DELETE);

        if (commentList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD);
        }

        ArrayList<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentDtoList.add(CommentDto.from(comment));
        }

        return commentDtoList;
    }

    /*
     * 신고 댓글 조회(관리자)
     */
    public List<CommentDto> getAccusedCommentList() {
        List<Comment> commentList = commentRepository.findByCommentStatus(CommentStatus.ACCUSE);

        if (commentList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_ACCUSED_COMMENT);
        }

        ArrayList<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            LocalDateTime latestAccusedAt = commentAccuseRepository.findTop1ByCommentSeq(
                    comment.getCommentSeq(), sortByCreatedAtDesc).orElseThrow(
                    () -> new DevGramException(CommentErrorCode.NOT_EXISTENT_ACCUSE_HISTORY))
                .getCreatedAt();
            commentDtoList.add(CommentDto.from(comment, latestAccusedAt));
        }

        return commentDtoList;
    }

    /*
     * 댓글 삭제
     */
    public CommentDto deleteComment(Long commentSeq) {
        Comment comment = commentRepository.findByCommentSeq(commentSeq)
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

        if (comment.getCommentStatus().equals(CommentStatus.DELETE)) {
            throw new DevGramException(CommentErrorCode.ALREADY_DELETED_COMMENT);
        }

        comment.setCommentStatus(CommentStatus.DELETE);

        return CommentDto.from(commentRepository.save(comment));
    }

    /*
     * 댓글 신고
     */
    public CommentAccuseDto accuseComment(CommentAccuseDto commentAccuseDto) {
        Comment comment = commentRepository.findByCommentSeq(commentAccuseDto.getCommentSeq())
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

        if (comment.getCommentStatus().equals(CommentStatus.POST)) {
            comment.setCommentStatus(CommentStatus.ACCUSE);
            commentRepository.save(comment);
        }

        CommentAccuse commentAccuse = CommentAccuse.builder()
            .commentSeq(commentAccuseDto.getCommentSeq())
            .accuseReason(commentAccuseDto.getAccuseReason())
            .build();

        return CommentAccuseDto.from(commentAccuseRepository.save(commentAccuse));
    }

    /*
     * 특정 신고 댓글 신고 내역 조회
     */
    public List<CommentAccuseDto> getAccusedCommentDetail(Long commentSeq) {
        List<CommentAccuse> commentAccuseList = commentAccuseRepository.findByCommentSeq(
                commentSeq, sortByCreatedAtDesc);

        if (commentAccuseList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_ACCUSE_HISTORY);
        }

        return CommentAccuseDto.fromList(commentAccuseList);
    }

    /*
     * 댓글 상태 업데이트(관리자)
     */
    public CommentDto updateCommentStatus(Long commentSeq, CommentStatus commentStatus) {
        Comment comment = commentRepository.findByCommentSeq(commentSeq)
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

        comment.setCommentStatus(commentStatus);

        commentRepository.save(comment);

        return CommentDto.from(comment);
    }

    // 생성 날짜 내림차순 정렬
//    public Sort sortByCreatedAtDesc() {
//        return Sort.by(Direction.DESC, "createdAt");
//    }
}
