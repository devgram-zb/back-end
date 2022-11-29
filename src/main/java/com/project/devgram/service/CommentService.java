package com.project.devgram.service;

import com.project.devgram.entity.Comment;
import com.project.devgram.model.comment.CommentDto;
import com.project.devgram.model.comment.CommentStatus;
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
        Comment comment = Comment.builder()
            .content(commentDto.getContent())
            .parentCommentSeq(commentDto.getParentCommentSeq())
            .boardSeq(commentDto.getBoardSeq())
            .createdAt(LocalDateTime.now())
            .createdBy(commentDto.getCreatedBy())
            .commentStatus(CommentStatus.POST)
            .build();

        return CommentDto.from(commentRepository.save(comment));
    }
}
