package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Category;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.CategoryRepository;
import com.project.devgram.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ImageUploader uploader;

	@Override
	public boolean write(ProductDto parameter, MultipartFile file) throws IOException {

		Category category = categoryRepository.findById(parameter.getCategory_Seq()).orElse(null);
		if (category == null) {
			return false;
		}

		String imageUrl = uploader.upload(file,"PRODUCT");

		Product product = Product.builder()
			.title(parameter.getTitle())
			.content(parameter.getContent())
			.price(parameter.getPrice())
			.hits(0)
			.likeCount(0)
			.rating(0.0)
			.status(Product.STATUS_CHECK)
			.category(category)
			.imageUrl(imageUrl)
			.build();

		productRepository.save(product);
		return true;
	}

	@Override
	public List<ProductDto> confirm(Pageable pageable) {
		List<Product> products = productRepository.findAll();
		return ProductDto.of(products);
	}

	@Override
	public List<ProductDto> list(Pageable pageable) {
		List<Product> products = productRepository.findAllByStatus(Product.STATUS_APPROVE,
			pageable);
		return ProductDto.of(products);
	}

	@Override
	public List<ProductDto> productList() { // 전체 리뷰 (페이징 X, 상태 Approve)
		List<Product> products = productRepository.findAllByStatus(Product.STATUS_APPROVE);
		return ProductDto.of(products);
	}

	@Override
	public boolean update(ProductDto parameter) {

		Long product_Seq = parameter.getProduct_Seq();

		Optional<Product> optionalProduct = productRepository.findById(product_Seq);
		if (!optionalProduct.isPresent()) {
			return false;
		}

		Category category = categoryRepository.findById(parameter.getCategory_Seq()).orElse(null);
		if (category == null) {
			return false;
		}

		Product product = optionalProduct.get();

		product.setPrice(parameter.getPrice());
		product.setTitle(parameter.getTitle());
		product.setContent(parameter.getContent());
		product.setStatus(parameter.getStatus());
		product.setCategory(category);
		productRepository.save(product);

		return true;
	}

	@Override
	public boolean delete(long id) {

		productRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductDto> popularList() {

		List<Product> products = productRepository.findTop5ByStatusOrderByHitsDesc(
			Product.STATUS_APPROVE);
		return ProductDto.of(products);
	}

	@Override
	public List<ProductDto> bestLikeList() {

		List<Product> product = productRepository.findTop8ByStatusOrderByLikeCountDesc(
			Product.STATUS_APPROVE);

		return ProductDto.of(product);
	}

	@Override
	public ProductDto detail(long id) {
		Product product = productRepository.findById(id).get();

		product.setHits(product.getHits() + 1);
		productRepository.save(product);

		return ProductDto.of(product);
	}


}
