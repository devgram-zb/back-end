package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.dto.CommentResponse.GroupComment;
import com.project.devgram.entity.Comment;
import com.project.devgram.entity.CommentAccuse;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.CommentErrorCode;
import com.project.devgram.repository.CommentAccuseRepository;
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

    @Mock
    private CommentAccuseRepository commentAccuseRepository;

    @DisplayName("그룹 댓글 등록 - 성공")
    @Test
    void addGroupComment_success() {
        // given
        CommentDto groupCommentDto = CommentDto.builder()
            .content("test")
            .boardSeq(1L)
            .createdBy("group")
            .build();

        Comment groupComment = Comment.builder()
            .commentSeq(1L)
            .content(groupCommentDto.getContent())
            .parentCommentSeq(groupCommentDto.getParentCommentSeq())
            .commentGroup(1L)
            .boardSeq(groupCommentDto.getBoardSeq())
            .createdBy(groupCommentDto.getCreatedBy())
            .commentStatus(CommentStatus.POST)
            .build();

        CommentDto childCommentDto = CommentDto.builder()
            .content("test")
            .boardSeq(1L)
            .parentCommentSeq(1L)
            .commentGroup(1L)
            .createdBy("child")
            .build();

        Comment childComment = Comment.builder()
            .commentSeq(2L)
            .boardSeq(1L)
            .parentCommentSeq(1L)
            .commentGroup(1L)
            .createdBy(childCommentDto.getCreatedBy())
            .build();

        given(commentRepository.save(any())).willReturn(groupComment);

        // when
        CommentDto result = commentService.addComment(groupCommentDto);

        // then
        assertEquals(result.getCommentSeq(), groupComment.getCommentSeq());
        assertEquals(result.getParentCommentSeq(), null);
        assertEquals(result.getCommentGroup(), groupComment.getCommentGroup());
    }

    @DisplayName("자식 댓글 등록 - 성공")
    @Test
    void addChildComment_success() {
        // given
        CommentDto childCommentDto = CommentDto.builder()
            .content("test")
            .boardSeq(1L)
            .parentCommentSeq(1L)
            .commentGroup(1L)
            .createdBy("child")
            .build();

        Comment childComment = Comment.builder()
            .commentSeq(2L)
            .boardSeq(childCommentDto.getBoardSeq())
            .parentCommentSeq(childCommentDto.getParentCommentSeq())
            .commentGroup(childCommentDto.getCommentGroup())
            .createdBy(childCommentDto.getCreatedBy())
            .build();

        given(commentRepository.save(any())).willReturn(childComment);
        given(commentRepository.findByCommentSeq(anyLong())).willReturn(Optional.of(Comment
            .builder()
            .createdBy("group")
            .build()));
        // when
        CommentDto result = commentService.addComment(childCommentDto);

        // then
        assertEquals(result.getCommentSeq(), childComment.getCommentSeq());
        assertEquals(result.getParentCommentSeq(), childComment.getParentCommentSeq());
        assertEquals(result.getCommentGroup(), childComment.getCommentGroup());
    }

    @DisplayName("댓글 조회 - 성공")
    @Test
    void getCommentList_success() {
        // given
        List<Comment> groupCommentList = new ArrayList<>();
        List<Comment> childCommentList = new ArrayList<>();

        for (long i = 0; i < 11; i++) {
            groupCommentList.add(Comment.builder()
                .commentSeq(i)
                .commentGroup(i)
                .boardSeq(1L)
                .content(i + "content")
                .commentStatus(CommentStatus.POST)
                .build());
        }

        for (long i = 0; i < 11; i++) {
            childCommentList.add(
                Comment.builder()
                    .commentSeq(i + 10L)
                    .commentGroup(1L)
                    .content(i + 10L + "content")
                    .commentStatus(CommentStatus.POST)
                    .build());
        }

        given(commentRepository.findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(1L,
            CommentStatus.DELETE)).willReturn(groupCommentList);

        given(commentRepository.findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNotNull(1L,
            CommentStatus.DELETE)).willReturn(childCommentList);

        // when
        List<GroupComment> result = commentService.getCommentList(1L);

        // then
        assertEquals(result.size(), 11);
        assertEquals(result.get(1).getChildCommentList().size(), 11);
        assertEquals(result.get(2).getChildCommentList().size(), 0);
    }

    @DisplayName("댓글 조회 - 실패 - 해당 게시글에 댓글 존재하지 않음")
    @Test
    void getCommentList_fail() {
        // given
        List<Comment> groupCommentList = new ArrayList<>();

        given(commentRepository.findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(1L,
            CommentStatus.DELETE)).willReturn(groupCommentList);

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.getCommentList(1L));

        // then
        assertEquals(devGramException.getErrorCode(),
            CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD);
    }

    @DisplayName("신고 댓글 조회 - 성공")
    @Test
    void getAccuseCommentList_success() {
        // given
        List<Comment> commentList = new ArrayList<>();

        for (long i = 0; i < 11; i++) {
            commentList.add(Comment.builder()
                .commentSeq(1L)
                .boardSeq(1L)
                .content(i + "content")
                .commentStatus(CommentStatus.ACCUSE)
                .build());
        }

        CommentAccuse commentAccuse = CommentAccuse.builder()
            .commentSeq(1L)
            .accuseReason("reason")
            .build();

        given(commentRepository.findByCommentStatus(CommentStatus.ACCUSE)).willReturn(commentList);

        given(commentAccuseRepository.findTop1ByCommentSeq(1L,
            CommentService.sortByCreatedAtDesc)).willReturn(Optional.of(commentAccuse));

        // when
        List<CommentDto> commentDtoList = commentService.getAccusedCommentList();

        // then
        assertEquals(commentDtoList.size(), 11);
    }

    @DisplayName("신고 댓글 조회 - 실패 - 신고 댓글이 존재하지 않음")
    @Test
    void getAccuseCommentList_fail() {
        // given
        List<Comment> commentList = new ArrayList<>();

        given(commentRepository.findByCommentStatus(CommentStatus.ACCUSE)).willReturn(commentList);

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.getAccusedCommentList());

        // then
        assertEquals(devGramException.getErrorCode(),
            CommentErrorCode.NOT_EXISTENT_ACCUSED_COMMENT);
    }

    @DisplayName("댓글 삭제 - 성공")
    @Test
    void deleteComment_success() {
        // given
        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.ACCUSE)
            .build();

        given(commentRepository.findByCommentSeq(1L)).willReturn(
            Optional.of(comment));

        given(commentRepository.save(comment)).willReturn(comment);

        // when
        CommentDto result = commentService.deleteComment(1L);

        // then
        assertEquals(result.getCommentStatus(), CommentStatus.DELETE);
    }

    @DisplayName("댓글 삭제 - 실패 - 존재하지 않는 댓글")
    @Test
    void deleteComment_fail_notExistent() {
        // given
        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.ACCUSE)
            .build();

        given(commentRepository.findByCommentSeq(1L)).willReturn(
            Optional.empty());

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.deleteComment(1L));

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.NOT_EXISTENT_COMMENT);
    }

    @DisplayName("댓글 삭제 - 실패 - 이미 삭제된 댓글")
    @Test
    void deleteComment_fail_alreadyDeleted() {
        // given
        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.DELETE)
            .build();

        given(commentRepository.findByCommentSeq(1L)).willReturn(
            Optional.of(comment));

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.deleteComment(1L));

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.ALREADY_DELETED_COMMENT);
    }

    @DisplayName("댓글 신고 - 성공")
    @Test
    void accuseComment_success() {
        // given
        CommentAccuseDto commentAccuseDto = CommentAccuseDto.builder()
            .commentAccuseSeq(1L)
            .commentSeq(1L)
            .accuseReason("testReason")
            .build();

        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.POST)
            .build();

        CommentAccuse commentAccuse = CommentAccuse.builder()
            .commentAccuseSeq(commentAccuseDto.getCommentAccuseSeq())
            .commentSeq(commentAccuseDto.getCommentSeq())
            .accuseReason(commentAccuseDto.getAccuseReason())
            .build();

        given(commentRepository.findByCommentSeq(commentAccuseDto.getCommentSeq())).willReturn(
            Optional.of(comment));

        given(commentRepository.save(comment)).willReturn(Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.ACCUSE)
            .build());

        given(commentAccuseRepository.save(any())).willReturn(commentAccuse);

        // when
        CommentAccuseDto result = commentService.accuseComment(commentAccuseDto);

        // then
        assertEquals(result.getCommentSeq(), commentAccuseDto.getCommentSeq());
        assertEquals(result.getCommentAccuseSeq(), commentAccuseDto.getCommentAccuseSeq());
        assertEquals(result.getAccuseReason(), commentAccuseDto.getAccuseReason());
    }

    @DisplayName("댓글 신고 - 실패 - 댓글이 존재하지 않음")
    @Test
    void accuseComment_fail_notExistent() {
        // given
        CommentAccuseDto commentAccuseDto = CommentAccuseDto.builder()
            .commentAccuseSeq(1L)
            .commentSeq(1L)
            .accuseReason("testReason")
            .build();

        given(commentRepository.findByCommentSeq(commentAccuseDto.getCommentSeq())).willReturn(
            Optional.empty());

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.accuseComment(commentAccuseDto));

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.NOT_EXISTENT_COMMENT);
    }

    @DisplayName("특정 신고 댓글 내역 조회 - 성공")
    @Test
    void getAccusedCommentDetail_success() {
        // given
        List<CommentAccuse> commentAccuseList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            commentAccuseList.add(CommentAccuse.builder()
                .commentAccuseSeq((long) i)
                .commentSeq(1L)
                .accuseReason("reason")
                .build());
        }

        given(commentAccuseRepository.findByCommentSeq(1L,
            CommentService.sortByCreatedAtDesc)).willReturn(commentAccuseList);

        // when
        List<CommentAccuseDto> commentAccuseDtoList = commentService.getAccusedCommentDetail(1L);

        // then
        assertEquals(commentAccuseDtoList.size(), commentAccuseList.size());
    }

    @DisplayName("특정 신고 댓글 내역 조회 - 실패 - 신고 내역 없음")
    @Test
    void getAccusedCommentDetail_fail_notExistent() {
        // given
        List<CommentAccuse> commentAccuseList = new ArrayList<>();

        given(commentAccuseRepository.findByCommentSeq(1L,
            CommentService.sortByCreatedAtDesc)).willReturn(commentAccuseList);

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> commentService.getAccusedCommentDetail(1L));

        // then
        assertEquals(devGramException.getErrorCode(), CommentErrorCode.NOT_EXISTENT_ACCUSE_HISTORY);
    }

    @DisplayName("댓글 상태 업데이트 - 성공")
    @Test
    void updateCommentStatus_success() {
        // given
        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.ACCUSE)
            .build();

        given(commentRepository.findByCommentSeq(1L)).willReturn(Optional.of(comment));

        given(commentRepository.save(comment)).willReturn(comment);

        // when
        CommentDto result1 = commentService.updateCommentStatus(1L, CommentStatus.POST);
        CommentDto result2 = commentService.updateCommentStatus(1L, CommentStatus.DELETE);

        // then
        assertEquals(result1.getCommentStatus(), CommentStatus.POST);
        assertEquals(result2.getCommentStatus(), CommentStatus.DELETE);
    }

    @DisplayName("댓글 상태 업데이트 - 실패 - 댓글이 존재하지 않음")
    @Test
    void updateCommentStatus_fail_notExistent() {
        // given
        Comment comment = Comment.builder()
            .commentSeq(1L)
            .commentStatus(CommentStatus.ACCUSE)
            .build();

        given(commentRepository.findByCommentSeq(1L)).willReturn(Optional.empty());

        // when
        DevGramException devGramException1 = assertThrows(DevGramException.class,
            () -> commentService.updateCommentStatus(1L, CommentStatus.POST));

        DevGramException devGramException2 = assertThrows(DevGramException.class,
            () -> commentService.updateCommentStatus(1L, CommentStatus.DELETE));

        // then
        assertEquals(devGramException1.getErrorCode(), CommentErrorCode.NOT_EXISTENT_COMMENT);
        assertEquals(devGramException2.getErrorCode(), CommentErrorCode.NOT_EXISTENT_COMMENT);
    }
}