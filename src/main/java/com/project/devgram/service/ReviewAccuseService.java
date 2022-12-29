package com.project.devgram.service;
import com.project.devgram.dto.ReviewAccuseDto;
import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.ReviewAccuse;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.ReviewAccuseErrorCode;
import com.project.devgram.exception.errorcode.ReviewErrorCode;
import com.project.devgram.repository.ReviewAccuseRepository;
import com.project.devgram.repository.ReviewRepository;
import com.project.devgram.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewAccuseService {

	private final ReviewAccuseRepository reviewAccuseRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	public static final Sort sortByReportedAtDesc = Sort.by(Direction.DESC, "reportAt");

	public ReviewAccuseDto reviewAccuse(ReviewAccuseDto parameter) {
		Users users = userRepository.findByUsername(parameter.getUsername()).orElse(null);
		if (users == null) {
			throw new DevGramException(ReviewErrorCode.USER_NOT_EXIST);
		}
		Review review = reviewRepository.findByReviewSeq(parameter.getReviewSeq()).orElse(null);
		if (review == null) {
			throw new DevGramException(ReviewAccuseErrorCode.REVIEW_NOT_EXIST);
		}

		if (review.getStatus().equals(Review.STATUS_POST)) {
			review.setStatus(Review.STATUS_ACCUSE);
			reviewRepository.save(review);
		} else if (review.getStatus().equals(Review.STATUS_ACCUSE)) {
			throw new DevGramException(ReviewAccuseErrorCode.ALREADY_REVIEW);
		} else if (review.getStatus().equals(Review.STATUS_DELETE)) {
			throw new DevGramException(ReviewAccuseErrorCode.ALREADY_REVIEW_DELETE);
		}

		ReviewAccuse reviewAccuse = ReviewAccuse.builder()
			.review(review)
			.content(parameter.getContent())
			.reportAt(LocalDateTime.now())
			.username(parameter.getUsername())
			.build();

		return ReviewAccuseDto.of(reviewAccuseRepository.save(reviewAccuse));
	}

	public List<ReviewDto> accuseReviewList() {

		List<Review> reviewList = reviewRepository.findByStatus(Review.STATUS_ACCUSE);

		if (reviewList.isEmpty()) {
			throw new DevGramException(ReviewAccuseErrorCode.REVIEW_ACCUSE_NOT_EXIST);
		}

		ArrayList<ReviewDto> reviewDtos = new ArrayList<>();
		for (Review review : reviewList) {
		LocalDateTime latestAccuseAt = reviewAccuseRepository.findTop1ByReviewReviewSeq(review.getReviewSeq(),sortByReportedAtDesc).orElseThrow(
			() -> new DevGramException(ReviewAccuseErrorCode.REVIEW_ACCUSE_NOT_EXIST)
		).getReportAt();
			reviewDtos.add(ReviewDto.of(review,latestAccuseAt));
		}
		return reviewDtos;
	}

	public boolean updateReviewStatus(Long reviewSeq, ReviewDto parameter){
		Review review = reviewRepository.findByReviewSeq(reviewSeq)
			.orElseThrow(() -> new DevGramException(ReviewAccuseErrorCode.REVIEW_NOT_EXIST));

		review.setStatus(parameter.getStatus());
		reviewRepository.save(review);

		return true;
	}

	public List<ReviewAccuseDto> accuseReviewDetail(Long reviewSeq){
		List<ReviewAccuse> reviewAccuseList = reviewAccuseRepository.findByReviewReviewSeq(reviewSeq, sortByReportedAtDesc);

		return ReviewAccuseDto.of(reviewAccuseList);

	}

}
