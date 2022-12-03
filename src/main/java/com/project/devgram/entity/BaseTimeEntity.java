package com.project.devgram.entity;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
	@CreatedDate
	private LocalDateTime createdAt;
	private String createdBy;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	private String updatedBy;


	//TODO : 하드 코딩된 내용 추후(login 완료 된 이후) 수정 예정
	@PrePersist
	public void prePersist() {
		this.createdBy = "devgram_test";
		this.updatedBy = "devgram_test";
	}

	@PreUpdate
	public void preUpdate(){
		this.updatedBy = "devgram_test";
	}
}
