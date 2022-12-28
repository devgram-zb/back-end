package com.project.devgram.service;

import com.project.devgram.dto.CommentAccuseDto;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.dto.CommentResponse.IncludedComment;
import com.project.devgram.dto.CommentResponse.GroupComment;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.Comment;
import com.project.devgram.entity.CommentAccuse;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardErrorCode;
import com.project.devgram.exception.errorcode.CommentErrorCode;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.BoardRepository;
import com.project.devgram.repository.CommentAccuseRepository;
import com.project.devgram.repository.CommentRepository;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.CommentStatus;
import com.project.devgram.util.pagerequest.PageRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentAccuseRepository commentAccuseRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    public static final Sort sortByCreatedAtDesc = Sort.by(Direction.DESC, "createdAt");

    /*
     * 댓글 등록
     */
    public CommentDto addComment(CommentDto commentDto) {

        Board board = boardRepository.findById(commentDto.getBoardSeq())
            .orElseThrow(() -> new DevGramException(BoardErrorCode.CANNOT_FIND_BOARD_BY_BOARDSEQ));

        // 대댓글이 아닌 경우
        if (commentDto.getParentCommentSeq() == null) {
            System.out.println(commentDto.getCreatedBy());
            Users users = userRepository.findByUsername(commentDto.getCreatedBy())
                .orElseThrow(() -> new DevGramException(UserErrorCode.USER_NOT_EXIST));

            Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .board(board)
                .createdBy(users)
                .updatedBy(users)
                .commentStatus(CommentStatus.POST)
                .build();

            comment = commentRepository.save(comment);
            comment.setCommentGroup(comment.getCommentSeq());

            return CommentDto.from(commentRepository.save(comment));

            // 대댓글인 경우
        } else {
            Users users = userRepository.findByUsername(commentDto.getCreatedBy())
                .orElseThrow(() -> new DevGramException(UserErrorCode.USER_NOT_EXIST));

            Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .parentCommentSeq(commentDto.getParentCommentSeq())
                .commentGroup(commentDto.getCommentGroup())
                .board(board)
                .createdBy(users)
                .updatedBy(users)
                .commentStatus(CommentStatus.POST)
                .build();

            String parentCommentCreatedBy = commentRepository.findByCommentSeq(
                    comment.getParentCommentSeq())
                .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT))
                .getCreatedBy().getUsername();

            comment.setParentCommentCreatedBy(parentCommentCreatedBy);

            return CommentDto.from(commentRepository.save(comment));
        }
    }

    /*
     * 댓글 조회(보드)
     */
    public List<GroupComment> getCommentList(Long boardSeq, PageRequest pageRequest) {

        // 부모 댓글 리스트
        Page<Comment> groupCommentList =
            commentRepository
                .findByBoard_BoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(
                    boardSeq,
                    CommentStatus.DELETE, pageRequest.of());

        if (groupCommentList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD);
        }

        ArrayList<GroupComment> groupCommentResponseList = new ArrayList<>();

        for (Comment comment : groupCommentList) {
            groupCommentResponseList.add(GroupComment.from(comment));
        }

        // 자식 댓글 리스트
        List<Comment> childCommentList =
            commentRepository
                .findByBoard_BoardSeqAndCommentStatusNotAndParentCommentSeqIsNotNullAndCommentGroupBetween(
                    boardSeq,
                    CommentStatus.DELETE,
                    groupCommentResponseList.get(0).getCommentGroup(),
                    groupCommentResponseList.get(groupCommentResponseList.size() - 1)
                        .getCommentGroup());

        ArrayList<IncludedComment> includedCommentResponseList = new ArrayList<>();

        // 자식 댓글이 존재할 경우, 부모 댓글의 필드(리스트)에 저장한다.
        if (!childCommentList.isEmpty()) {
            for (Comment comment : childCommentList) {

                includedCommentResponseList.add(IncludedComment.from(comment));
            }

            groupCommentResponseList.stream()
                .forEach(group -> group.setIncludedCommentList(includedCommentResponseList.stream()
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
            LocalDateTime latestAccusedAt = commentAccuseRepository.findTop1ByComment_CommentSeq(
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
            List<Comment> targetList = commentRepository.findByCommentGroup(
                comment.getCommentGroup());

            for (Comment target : targetList) {
                target.setCommentStatus(CommentStatus.DELETE);
                commentRepository.save(target);
            }

            return String.format("%d번 commentGroup에 해당하는 댓글들이 삭제 되었습니다.", comment.getCommentGroup());
        }

        // 부모 댓글이 삭제되는 경우 -> 해당 댓글과 자식 댓글 삭제
        List<Comment> targetList = commentRepository.findByParentCommentSeq(
            comment.getCommentSeq());

        if (!targetList.isEmpty()) {
            // 부모 댓글 삭제
            comment.setCommentStatus(CommentStatus.DELETE);
            commentRepository.save(comment);

            // 자식 댓글 삭제
            for (Comment target : targetList) {
                target.setCommentStatus(CommentStatus.DELETE);
                commentRepository.save(target);
            }

            return String.format("%d번 댓글과 해당 댓글의 자식 댓글들이 삭제 되었습니다.", comment.getCommentSeq());

            // 자식 댓글이 삭제되는 경우 -> 해당 댓글만 삭제
        } else {
            comment.setCommentStatus(CommentStatus.DELETE);
            commentRepository.save(comment);

            return String.format("%d번 댓글이 삭제 되었습니다.", comment.getCommentSeq());
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
            comment = commentRepository.save(comment);
        }

        Users users = userRepository.findByUsername(commentAccuseDto.getCreatedBy())
            .orElseThrow(() -> new DevGramException(
                UserErrorCode.USER_NOT_EXIST));

        CommentAccuse commentAccuse = CommentAccuse.builder()
            .comment(comment)
            .createdBy(users)
            .accuseReason(commentAccuseDto.getAccuseReason())
            .build();

        return CommentAccuseDto.from(commentAccuseRepository.save(commentAccuse));
    }

    /*
     * 특정 신고 댓글 신고 내역 조회
     */
    public List<CommentAccuseDto> getAccusedCommentDetail(Long commentSeq) {
        List<CommentAccuse> commentAccuseList = commentAccuseRepository.findByComment_CommentSeq(
            commentSeq, sortByCreatedAtDesc);

        if (commentAccuseList.isEmpty()) {
            throw new DevGramException(CommentErrorCode.NOT_EXISTENT_ACCUSE_HISTORY);
        }

        return CommentAccuseDto.fromList(commentAccuseList);
    }

    /*
     * 댓글 상태 업데이트(관리자)
     */
    public String updateCommentStatus(Long commentSeq, CommentStatus commentStatus) {

        if (commentStatus.equals(CommentStatus.POST) || commentStatus.equals(CommentStatus.ACCUSE)) {
            Comment comment = commentRepository.findByCommentSeq(commentSeq)
                .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

            comment.setCommentStatus(commentStatus);

            commentRepository.save(comment);

            return String.format("%d번 댓글이 %s 상태로 변경되었습니다.", commentSeq, commentStatus);

        } else {
            return deleteComment(commentSeq);
        }
    }

    /*
     * 댓글 내용 수정(작성자)
     */
    public CommentDto updateCommentContent(CommentDto commentDto) {
        Comment comment = commentRepository.findByCommentSeq(commentDto.getCommentSeq())
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_COMMENT));

        Users users = userRepository.findByUsername(commentDto.getUpdatedBy())
            .orElseThrow(() -> new DevGramException(UserErrorCode.USER_NOT_EXIST));

        comment.setContent(commentDto.getContent());
        comment.setUpdatedBy(users);

        return CommentDto.from(commentRepository.save(comment));
    }
}
