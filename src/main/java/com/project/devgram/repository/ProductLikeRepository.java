package com.project.devgram.repository;

import com.project.devgram.entity.ProductLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

	Optional<ProductLike> findProductLikeByUsernameAndProductProductSeq(String username,
		Long productSeq); // 게시글 + users 조합

	List<ProductLike> findAllByUsername(String username);

}
