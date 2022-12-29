package com.project.devgram.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.devgram.type.ReviewCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review implements ReviewCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewSeq;
	private double mark;
	private String content;
	private String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	private String username;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productSeq")
	@JsonBackReference
	private Product product;

	@Builder.Default
	@OneToMany(mappedBy = "review")
	@JsonManagedReference
	private List<ReviewAccuse> reviewAccuseList = new ArrayList<>();
}
