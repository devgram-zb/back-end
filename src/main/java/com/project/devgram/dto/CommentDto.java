package com.project.devgram.dto;

import com.project.devgram.entity.Comment;
import com.project.devgram.repository.CommentAccuseRepository;
import com.project.devgram.service.CommentService;
import com.project.devgram.type.CommentStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {

    private LocalDateTime latestAccusedAt;

    private Long commentSeq;
    private String content;
    private Long parentCommentSeq;
    private Long commentGroup;
    private Long boardSeq;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private CommentStatus commentStatus;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
            .commentSeq(comment.getCommentSeq())
            .content(comment.getContent())
            .parentCommentSeq(comment.getParentCommentSeq())
            .commentGroup(comment.getCommentGroup())
            .boardSeq(comment.getBoard().getBoardSeq())
            .createdAt(comment.getCreatedAt())
            .createdBy(comment.getCreatedBy().getUsername())
            .updatedAt(comment.getUpdatedAt())
            .updatedBy(comment.getUpdatedBy().getUsername())
            .commentStatus(comment.getCommentStatus())
            .build();

//        if (comment.getUpdatedBy() != null) {
//            commentDto.setUpdatedBy(comment.getUpdatedBy().getUsername());
//        }

        return commentDto;
    }

    public static CommentDto from(Comment comment, LocalDateTime latestAccusedAt) {
        CommentDto commentDto = CommentDto.builder()
            .latestAccusedAt(latestAccusedAt)
            .commentSeq(comment.getCommentSeq())
            .content(comment.getContent())
            .parentCommentSeq(comment.getParentCommentSeq())
            .commentGroup(comment.getCommentGroup())
            .boardSeq(comment.getBoard().getBoardSeq())
            .createdAt(comment.getCreatedAt())
            .createdBy(comment.getCreatedBy().getUsername())
            .updatedAt(comment.getUpdatedAt())
            .updatedBy(comment.getUpdatedBy().getUsername())
            .commentStatus(comment.getCommentStatus())
            .build();

//        if (comment.getUpdatedBy() != null) {
//            commentDto.setUpdatedBy(comment.getUpdatedBy().getUsername());
//        }

        return commentDto;
    }
}
