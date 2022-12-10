package com.project.devgram.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "board_like",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "board_user_constratint",
			columnNames = {"board_seq", "user_seq"}
		)
	}
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLike extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_like_seq", nullable = false)
	private Long boardLikeSeq;

	@ManyToOne
	@JoinColumn(name = "board_seq")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "user_seq")
	private User user;
}
