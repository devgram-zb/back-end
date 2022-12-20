package com.project.devgram.service;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.dto.CommentResponse.ChildComment;
import com.project.devgram.dto.CommentResponse.GroupComment;
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
import java.util.Objects;
import java.util.stream.Collectors;
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

        // 대댓글이 아닌 경우
        if (commentDto.getParentCommentSeq() == null) {
            Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .boardSeq(commentDto.getBoardSeq())
                .createdBy(commentDto.getCreatedBy())
                .commentStatus(CommentStatus.POST)
                .build();

            comment = commentRepository.save(comment);
            comment.setCommentGroup(comment.getCommentSeq());

            return CommentDto.from(commentRepository.save(comment));

            // 대댓글인 경우
        } else {
            Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .parentCommentSeq(commentDto.getParentCommentSeq())
                .commentGroup(commentDto.getCommentGroup())
                .boardSeq(commentDto.getBoardSeq())
                .createdBy(commentDto.getCreatedBy())
                .commentStatus(CommentStatus.POST)
                .build();

            String parentCommentCreatedBy = commentRepository.findByCommentSeq(
                    comment.getParentCommentSeq())
                .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT))
                .getCreatedBy();

            comment.setParentCommentCreatedBy(parentCommentCreatedBy);

            return CommentDto.from(commentRepository.save(comment));
        }
    }

    /*
     * 댓글 조회(보드)
     */
    public List<GroupComment> getCommentList(Long boardSeq) {

        // 부모 댓글 리스트
        List<Comment> groupCommentList = commentRepository.findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(
            boardSeq,
            CommentStatus.DELETE);

        if (groupCommentList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD);
        }

        ArrayList<GroupComment> groupCommentResponseList = new ArrayList<>();

        for (Comment comment : groupCommentList) {
            groupCommentResponseList.add(GroupComment.from(comment));
        }

        // 자식 댓글 리스트
        List<Comment> childCommentList = commentRepository.findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNotNull(
            boardSeq, CommentStatus.DELETE);

        ArrayList<ChildComment> childCommentResponseList = new ArrayList<>();

        // 자식 댓글이 존재할 경우, 부모 댓글의 필드(리스트)에 저장한다.
        if (!childCommentList.isEmpty()) {
            for (Comment comment : childCommentList) {

                childCommentResponseList.add(ChildComment.from(comment));
            }

            groupCommentResponseList.stream()
                .forEach(group -> group.setChildCommentList(childCommentResponseList.stream()
                    .filter(child -> child.getCommentGroup().equals(group.getCommentGroup()))
                    .collect(
                        Collectors.toList())));
        }

        return groupCommentResponseList;
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
    public String deleteComment(Long commentSeq) {
        Comment comment = commentRepository.findByCommentSeq(commentSeq)
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

        if (comment.getCommentStatus().equals(CommentStatus.DELETE)) {
            throw new DevGramException(CommentErrorCode.ALREADY_DELETED_COMMENT);
        }

        // 그룹 댓글이 삭제되는 경우 -> 그룹에 속한 댓글 모두 삭제
        if (Objects.equals(comment.getCommentSeq(), comment.getCommentGroup())) {
            List<Comment> targetList = commentRepository.findByCommentGroup(comment.getCommentGroup());

            for (Comment target: targetList) {
                target.setCommentStatus(CommentStatus.DELETE);
                commentRepository.save(target);
            }

            return "해당 commentGroup에 속한 댓글들이 삭제 되었습니다.";
        }

        // 부모 댓글이 삭제되는 경우 -> 해당 댓글과 자식 댓글 삭제
        List<Comment> targetList = commentRepository.findByParentCommentSeq(comment.getCommentSeq());

        if (!targetList.isEmpty()) {
            // 부모 댓글 삭제
            comment.setCommentStatus(CommentStatus.DELETE);
            commentRepository.save(comment);

            // 자식 댓글 삭제
            for (Comment target: targetList) {
                target.setCommentStatus(CommentStatus.DELETE);
                commentRepository.save(target);
            }

            return "부모 댓글과 자식 댓글이 삭제되었습니다.";

        // 자식 댓글이 삭제되는 경우 -> 해당 댓글만 삭제
        } else {
            comment.setCommentStatus(CommentStatus.DELETE);
            commentRepository.save(comment);

            return "자식 댓글이 삭제되었습니다.";
        }
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
}
