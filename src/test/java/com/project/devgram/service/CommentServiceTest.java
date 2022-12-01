package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.project.devgram.dto.CommentDto;
import com.project.devgram.entity.Comment;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.CommentErrorCode;
import com.project.devgram.repository.CommentRepository;
import com.project.devgram.type.CommentStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            .commentStatus(CommentStatus.POST)
            .build();

        System.out.println(comment.getCommentSeq());

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

    @DisplayName("댓글 조회 - 성공")
    @Test
    void getCommentList_success() {
        // given
        List<Comment> commentList = new ArrayList<>();

        for (long i = 0; i < 11; i++) {
            commentList.add(Comment.builder()
                .commentSeq(i)
                .boardSeq(1L)
                .content(i + "content")
                .commentStatus(CommentStatus.POST)
                .build());
        }

        given(commentRepository.findByBoardSeqAndCommentStatusNot(1L, CommentStatus.DELETE)).willReturn(
            Optional.of(commentList));

        // when
        List<CommentDto> commentDtoList = commentService.getCommentList(1L);

        // then
        assertEquals(commentDtoList.size(), 11);
    }

    @DisplayName("댓글 조회 - 실패 - 해당 게시글에 댓글 존재하지 않음")
    @Test
    void getCommentList_fail() {
        // given

        given(commentRepository.findByBoardSeqAndCommentStatusNot(1L, CommentStatus.DELETE)).willReturn(Optional.empty());

        // when
        DevGramException devGramException = assertThrows(DevGramException.class, () -> commentService.getCommentList(1L));

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD);
    }

    @DisplayName("신고 댓글 조회 - 성공")
    @Test
    void getAccuseCommentList_success() {
        // given
        List<Comment> commentList = new ArrayList<>();

        for (long i = 0; i < 11; i++) {
            commentList.add(Comment.builder()
                .commentSeq(i)
                .boardSeq(1L)
                .content(i + "content")
                .commentStatus(CommentStatus.ACCUSE)
                .build());
        }

        given(commentRepository.findByCommentStatus(CommentStatus.ACCUSE)).willReturn(
            Optional.of(commentList));

        // when
        List<CommentDto> commentDtoList = commentService.getAccusedCommentList();

        // then
        assertEquals(commentDtoList.size(), 11);
    }

    @DisplayName("신고 댓글 조회 - 실패 - 신고 댓글이 존재하지 않음")
    @Test
    void getAccuseCommentList_fail() {
        // given

        given(commentRepository.findByCommentStatus(CommentStatus.ACCUSE)).willReturn(Optional.empty());

        // when
        DevGramException devGramException = assertThrows(DevGramException.class, () -> commentService.getAccusedCommentList());

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.NOT_EXISTENT_ACCUSED_COMMENT);
    }
}