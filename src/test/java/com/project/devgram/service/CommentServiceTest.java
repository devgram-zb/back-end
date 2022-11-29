package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.project.devgram.domain.comment.Comment;
import com.project.devgram.domain.comment.CommentDto;
import com.project.devgram.domain.comment.CommentStatus;
import com.project.devgram.repository.CommentRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("댓글 등록 - 성공")
    @Test
    void addComment_success() {
        // given
        CommentDto commentDto = CommentDto.builder()
            .content("test")
            .parentCommentSeq(1L)
            .boardSeq(1L)
            .createdBy("writer")
            .build();

        Comment comment = Comment.builder()
            .commentSeq(1L)
            .content(commentDto.getContent())
            .parentCommentSeq(commentDto.getParentCommentSeq())
            .boardSeq(commentDto.getBoardSeq())
            .createdBy(commentDto.getCreatedBy())
            .createdAt(LocalDateTime.now())
            .commentStatus(CommentStatus.POST)
            .build();

        given(commentRepository.save(any())).willReturn(comment);

        // when
        CommentDto result = commentService.addComment(commentDto);

        // then
        assertEquals(result.getCommentSeq(), comment.getCommentSeq());
        assertEquals(result.getContent(), comment.getContent());
        assertEquals(result.getParentCommentSeq(), comment.getParentCommentSeq());
        assertEquals(result.getCreatedAt(), comment.getCreatedAt());
        assertEquals(result.getCreatedBy(), comment.getCreatedBy());
    }
}