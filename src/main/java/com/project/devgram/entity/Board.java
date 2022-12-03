package com.project.devgram.entity;

import com.project.devgram.type.status.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_seq", nullable = false)
	private Long boardSeq;

	@Column(length = 100, name= "title")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(length = 10)
	private Status status;

	@Column(name="like_count")
	private Integer likeCount;

	@Builder
	public Board(String title, String content){
		this.title = title;
		this.content = content;
		this.status = Status.NORMAL;
		this.likeCount = 0;
	}
}
