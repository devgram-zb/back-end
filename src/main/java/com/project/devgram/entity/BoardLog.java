package com.project.devgram.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardLog extends BaseTimeEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_log_seq", nullable = false)
	private Long boardLogSeq;

	@Column(columnDefinition = "TEXT")
	private String beforeContent;

	@ManyToOne
	@JoinColumn(name = "board_seq") // 외래키
	private Board board;
}
