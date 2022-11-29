package com.project.devgram.service;

import com.project.devgram.domain.comment.Comment;
import com.project.devgram.domain.comment.CommentDto;
import com.project.devgram.domain.comment.CommentStatus;
import com.project.devgram.repository.CommentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /*
     * 댓글 등록
     */
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = commentRepository.save(Comment.builder()
            .parentCommentSeq(commentDto.getParentCommentSeq())
            .boardSeq(commentDto.getBoardSeq())
            .createdAt(LocalDateTime.now())
            .createdBy(commentDto.getCreatedBy())
            .content(commentDto.getContent())
            .commentStatus(CommentStatus.POST)
            .build());

        return CommentDto.from(comment);
    }
}
