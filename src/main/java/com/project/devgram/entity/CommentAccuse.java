package com.project.devgram.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
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
@AttributeOverride(name = "createdAt", column = @Column(name = "reportedAt"))
@AttributeOverride(name = "createdBy", column = @Column(name = "reportedBy"))
public class CommentAccuse extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentAccuseSeq;

    private String content;

    private Long commentSeq;
}
