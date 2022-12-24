package com.project.devgram.dto;

import com.project.devgram.entity.Comment;
import com.project.devgram.type.CommentStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class CommentResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class GroupComment {
        private Long commentSeq;
        private String content;
        private Long parentCommentSeq;
        private Long commentGroup;
        private Long boardSeq;
        private LocalDateTime createdAt;
        private String createdBy;
        private CommentStatus commentStatus;

        private List<IncludedComment> includedCommentList; // 자식 댓글 리스트

        public static GroupComment from(Comment comment) {
            return GroupComment.builder()
                .commentSeq(comment.getCommentSeq())
                .content(comment.getContent())
                .parentCommentSeq(comment.getParentCommentSeq())
                .commentGroup(comment.getCommentGroup())
                .boardSeq(comment.getBoard().getBoardSeq())
                .createdAt(comment.getCreatedAt())
                .createdBy(comment.getCreatedBy())
                .commentStatus(comment.getCommentStatus())
                .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class IncludedComment {
        private Long commentSeq;
        private String content;
        private Long parentCommentSeq;
        private String parentCommentCreatedBy;
        private Long commentGroup;
        private Long boardSeq;
        private LocalDateTime createdAt;
        private String createdBy;
        private CommentStatus commentStatus;

        public static IncludedComment from(Comment comment) {
            return IncludedComment.builder()
                .commentSeq(comment.getCommentSeq())
                .content(comment.getContent())
                .parentCommentSeq(comment.getParentCommentSeq())
                .parentCommentCreatedBy(comment.getParentCommentCreatedBy())
                .commentGroup(comment.getCommentGroup())
                .boardSeq(comment.getBoard().getBoardSeq())
                .createdAt(comment.getCreatedAt())
                .createdBy(comment.getCreatedBy())
                .commentStatus(comment.getCommentStatus())
                .build();
        }
    }

}
