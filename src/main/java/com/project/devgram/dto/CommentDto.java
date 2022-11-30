package com.project.devgram.dto;

import com.project.devgram.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentDto {
    private Long commentSeq;
    private String content;
    private Long parentCommentSeq;
    private Long boardSeq;
    private LocalDateTime createdAt;
    private String createdBy;

    public static CommentDto from(Comment comment) {
        return new CommentDto(comment.getCommentSeq(), comment.getContent(),
            comment.getParentCommentSeq(), comment.getBoardSeq(), comment.getCreatedAt(),
            comment.getCreatedBy());
    }
}
