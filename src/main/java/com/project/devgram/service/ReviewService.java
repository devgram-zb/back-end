package com.project.devgram.service;

import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Product;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.ReviewAccuseErrorCode;
import com.project.devgram.exception.errorcode.ReviewErrorCode;
import com.project.devgram.repository.ProductRepository;
import com.project.devgram.repository.ReviewRepository;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ReviewCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public boolean ReviewWrite(ReviewDto parameter) {
		Users users = userRepository.findByUsername(parameter.getUsername()).orElse(null);
		if (users == null) {
			throw new DevGramException(ReviewErrorCode.USER_NOT_EXIST);
		}

		Product product = productRepository.findById(parameter.getProductSeq()).orElse(null);
		if (product == null) {
			throw new DevGramException(ReviewErrorCode.PRODUCT_NOT_EXIST);
		}

		Review review = reviewRepository.findByUsernameAndProduct(parameter.getUsername(), product);

		if (review != null) {
			throw new DevGramException(ReviewErrorCode.ALREADY_REVIEW);
		}

			 review = Review.builder()
			.content(parameter.getContent())
			.mark(parameter.getMark())
			.status(Review.STATUS_POST)
			.createdAt(LocalDateTime.now())
			.product(product)
			.username(parameter.getUsername())
			.build();

		reviewRepository.save(review);
		product.setReviewCount(product.getReviewCount() + 1);
		product.setTotalRating(product.getTotalRating() + parameter.getMark());
		product.setRating(product.getTotalRating() / product.getReviewCount());
		productRepository.save(product);
		product.addReview(review);
		return true;

	}

	public List<ReviewDto> adminList(Pageable pageable) {
		List<Review> reviews = reviewRepository.findAll();
		return ReviewDto.of(reviews);

	}

	public List<ReviewDto> Reviewlist(Long productSeq) {
		List<Review> reviews = reviewRepository.findByProductProductSeqAndStatusNot(productSeq,
			Review.STATUS_DELETE);
		if (reviews == null) {
			throw new DevGramException(ReviewErrorCode.REVIEW_NOT_EXIST);
		}
		return ReviewDto.of(reviews);
	}

	public boolean updateReview(ReviewDto parameter) {

		Optional<Review> optionalReview = reviewRepository.findByReviewSeq(
			parameter.getReviewSeq());
		if (!optionalReview.isPresent()) {
			return false;
		}

		Product product = productRepository.findById(parameter.getProductSeq()).orElse(null);
		if (product == null) {
			throw new DevGramException(ReviewErrorCode.PRODUCT_NOT_EXIST);
		}

		Review review = optionalReview.get();
		double presentMark = review.getMark();

		review.setContent(parameter.getContent());
		review.setMark(parameter.getMark());
		double newMark = review.getMark();

		product.setTotalRating(product.getTotalRating() - presentMark + newMark);
		product.setRating(product.getTotalRating() / product.getReviewCount());
		productRepository.save(product);

		reviewRepository.save(review);
		return true;

	}

	public boolean deleteReview(ReviewDto parameter) {
		Review review = reviewRepository.findByReviewSeq(parameter.getReviewSeq()).orElse(null);

		if (review == null) {
			throw new DevGramException(ReviewAccuseErrorCode.REVIEW_NOT_EXIST);
		}

		Product product = productRepository.findById(parameter.getProductSeq()).orElse(null);
		if (product == null) {
			throw new DevGramException(ReviewErrorCode.PRODUCT_NOT_EXIST);
		}

		double presentMark = review.getMark();

		review.setStatus(ReviewCode.STATUS_DELETE);

		product.setReviewCount(product.getReviewCount() - 1);
		product.setTotalRating(product.getTotalRating() - presentMark);
		product.setRating(product.getTotalRating() / product.getReviewCount());
		productRepository.save(product);

		reviewRepository.save(review);
		return true;
	}
}