package com.project.devgram.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.project.devgram.entity.ReviewAccuse;
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
public class ReviewAccuseDto {

	private Long reviewAccuseSeq;

	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime reportAt;
	private Long reviewSeq;
	private String username;

	public static List<ReviewAccuseDto> of(List<ReviewAccuse> reviewAccuses) {
		if (reviewAccuses != null) {
			List<ReviewAccuseDto> reviewAccuseList = new ArrayList<>();
			for (ReviewAccuse x : reviewAccuses) {
				reviewAccuseList.add(of(x));
			}
			return reviewAccuseList;
		}
		return null;
	}

	public static ReviewAccuseDto of(ReviewAccuse reviewAccuse) {
		return ReviewAccuseDto.builder()
			.reviewAccuseSeq(reviewAccuse.getReviewAccuseSeq())
			.content(reviewAccuse.getContent())
			.reportAt(reviewAccuse.getReportAt())
			.reviewSeq(reviewAccuse.getReview().getReviewSeq())
			.username(reviewAccuse.getUsername())
			.build();
	}

}
