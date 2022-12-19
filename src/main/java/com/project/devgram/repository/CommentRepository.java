package com.project.devgram.repository;

import com.project.devgram.entity.Comment;
import com.project.devgram.type.CommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentSeq(Long commentSeq);

    // 특정 보드의 부모 댓글 리스트
    List<Comment> findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNull(Long boardSeq, CommentStatus commentStatus);

    // 특정 보드의 자식 댓글 리스트
    List<Comment> findByBoardSeqAndCommentStatusNotAndParentCommentSeqIsNotNull(Long boardSeq, CommentStatus commentStatus);

    List<Comment> findByCommentStatus(CommentStatus commentStatus);
}
