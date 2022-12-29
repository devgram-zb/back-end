package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import com.project.devgram.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review findByUsernameAndProduct(String username, Product product);
	Optional<Review> findByReviewSeq(Long reviewSeq);
	List<Review> findByStatus(String status);
	List<Review> findByProductProductSeqAndStatusNot(Long productSeq, String status);
}
