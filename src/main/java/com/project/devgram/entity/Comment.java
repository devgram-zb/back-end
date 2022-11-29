package com.project.devgram.entity;

import com.project.devgram.model.comment.CommentStatus;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentSeq;

    private String content;
    private Long parentCommentSeq;
    private Long boardSeq;

    private LocalDateTime createdAt;
    private String createdBy;

    private CommentStatus commentStatus;

}
