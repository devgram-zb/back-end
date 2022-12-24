package com.project.devgram.entity;

import com.project.devgram.type.CommentStatus;
import java.time.LocalDateTime;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentSeq; // 댓글 번호

    private String content; // 댓글 내용
    private Long commentGroup; // 댓글 그룹
    private Long parentCommentSeq; // 부모 댓글 번호
    private String parentCommentCreatedBy; // 부모 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board; // 게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_By", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Users createdBy; // 작성자

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Users updatedBy; // 수정자

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus; // 댓글 상태
}
