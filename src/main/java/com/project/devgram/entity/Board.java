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
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_seq", nullable = false)
	private Long boardSeq;

	@Column(length = 100, name = "title")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(length = 10)
	private Status status;

	@Column(name = "like_count")
	private Integer likeCount;

	@Builder
	public Board(String title, String content) {
		this.title = title;
		this.content = content;
		this.status = Status.NORMAL;
		this.likeCount = 0;
	}

	public void deleteBoard() {
		this.status = Status.DELETED;
	}

	public void updateTitle(String title) {
		checkIsDeleted();
		if (StringUtils.hasText(title)) {
			this.title = title;
		}
	}

	public void updateContent(String content) {
		checkIsDeleted();
		if (StringUtils.hasText(content)) {
			this.content = content;
		}
	}

	public void increaseLikeCount(){
		this.likeCount = this.likeCount + 1;
	}

	public void decreaseLikeCount(){
		if(this.likeCount == 0){
			throw new IndexOutOfBoundsException("좋아요는 - 가 될수 없습니다.");
		}
		this.likeCount = this.likeCount - 1;
	}

	private void checkIsDeleted() {
		//TODO : MERGE 이후 적절한 Exception 만들어 해당 내용 변경
		if (this.status == Status.DELETED) {
			throw new RuntimeException("이미 삭제된 보드 입니다. ");
		}
	}

}
