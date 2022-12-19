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
    private CommentStatus commentStatus;

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
            .commentSeq(comment.getCommentSeq())
            .content(comment.getContent())
            .parentCommentSeq(comment.getParentCommentSeq())
            .commentGroup(comment.getCommentGroup())
            .boardSeq(comment.getBoardSeq())
            .createdAt(comment.getCreatedAt())
            .createdBy(comment.getCreatedBy())
            .commentStatus(comment.getCommentStatus())
            .build();
    }

    public static CommentDto from(Comment comment, LocalDateTime latestAccusedAt) {
        return CommentDto.builder()
            .latestAccusedAt(latestAccusedAt)
            .commentSeq(comment.getCommentSeq())
            .content(comment.getContent())
            .parentCommentSeq(comment.getParentCommentSeq())
            .commentGroup(comment.getCommentGroup())
            .boardSeq(comment.getBoardSeq())
            .createdAt(comment.getCreatedAt())
            .createdBy(comment.getCreatedBy())
            .commentStatus(comment.getCommentStatus())
            .build();
    }
}
