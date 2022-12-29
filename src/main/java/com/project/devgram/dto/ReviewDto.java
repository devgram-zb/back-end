package com.project.devgram.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.project.devgram.entity.Review;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime latestAccusedAt;

	private Long reviewSeq;
	private double mark;
	private String content;
	private String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	private String username;
	private Long productSeq;

	public static List<ReviewDto> of(List<Review> reviews) {
		if (reviews != null) {
			List<ReviewDto> reviewList = new ArrayList<>();
			for (Review x : reviews) {
				reviewList.add(of(x));
			}
			return reviewList;
		}
		return null;
	}

	public static ReviewDto of(Review review) {
		return ReviewDto.builder()
			.reviewSeq(review.getReviewSeq())
			.content(review.getContent())
			.mark(review.getMark())
			.status(review.getStatus())
			.createdAt(review.getCreatedAt())
			.productSeq(review.getProduct().getProductSeq())
			.username(review.getUsername())
			.build();
	}

	public static ReviewDto of(Review review, LocalDateTime latestAccusedAt){
		return ReviewDto.builder()
			.latestAccusedAt(latestAccusedAt)
			.reviewSeq(review.getReviewSeq())
			.content(review.getContent())
			.mark(review.getMark())
			.status(review.getStatus())
			.createdAt(review.getCreatedAt())
			.productSeq(review.getProduct().getProductSeq())
			.username(review.getUsername())
			.build();
	}
}
