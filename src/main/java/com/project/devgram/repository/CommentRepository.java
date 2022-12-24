package com.project.devgram.repository;

import com.project.devgram.entity.Comment;
import com.project.devgram.type.CommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentSeq(Long commentSeq);

    List<Comment> findByCommentGroup(Long commentGroup);

    List<Comment> findByParentCommentSeq(Long parentCommentSeq);

    // 특정 보드의 그룹 댓글 리스트
    Page<Comment> findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(Long boardSeq, CommentStatus commentStatus, Pageable pageable);

    // 특정 보드의 자식 댓글 리스트
    List<Comment> findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNotNullAndCommentGroupBetween(Long boardSeq, CommentStatus commentStatus, Long startPS, Long endPS);

    List<Comment> findByCommentStatus(CommentStatus commentStatus);
}
