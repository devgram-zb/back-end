package com.project.devgram.service;

import com.project.devgram.dto.ProductLikeDto;
import com.project.devgram.entity.Product;
import com.project.devgram.entity.ProductLike;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.ProductLikeRepository;
import com.project.devgram.repository.ProductRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

	private final ProductLikeRepository productLikeRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public void productLike(ProductLikeDto productLikeDto) {

		Product product = productRepository.findById(productLikeDto.getProductSeq()).orElse(null);

		Users users = userRepository.findByUsername(productLikeDto.getUsername()).orElse(null);
		if (users == null){
			throw new DevGramException(UserErrorCode.USER_NOT_EXIST);
		}

		ProductLike productLike = ProductLike.builder()
			.product(product)
			.username(productLikeDto.getUsername())
			.build();
		productLikeRepository.save(productLike);

		product.setLikeCount(product.getLikeCount() + 1);
		productRepository.save(product);

	}

	public void productUnLike(ProductLikeDto productLikeDto) {

		Product product = productRepository.findById(productLikeDto.getProductSeq()).orElse(null);

		Users users = userRepository.findByUsername(productLikeDto.getUsername()).orElse(null);
		if (users == null){
			throw new DevGramException(UserErrorCode.USER_NOT_EXIST);
		}
		Optional<ProductLike> productLike = findProductLikeByUsernameAndProductSeq(productLikeDto);

		productLikeRepository.delete(productLike.get());

		product.setLikeCount(product.getLikeCount() - 1);
		productRepository.save(product);

	}

	public Optional<ProductLike> findProductLikeByUsernameAndProductSeq(
		ProductLikeDto productLikeDto) {
		Product product = productRepository.findById(productLikeDto.getProductSeq()).orElse(null);

		return productLikeRepository.findProductLikeByUsernameAndProductProductSeq(
			productLikeDto.getUsername(),
			productLikeDto.getProductSeq());
	}

	public List<ProductLikeDto> list(String username) {

		List<ProductLike> productLikes = productLikeRepository.findAllByUsername(username);
		return ProductLikeDto.of(productLikes);
	}
}
