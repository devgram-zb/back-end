package com.project.devgram.controller;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping // 일반회원 글작성
	public boolean write(@RequestBody ProductDto parameter) {
		return productService.write(parameter);
	}

	@GetMapping("/admin") // admin 페이지 - 전체조회 (paging limit 5 내림차순)
	public List<ProductDto> confirm(@PageableDefault(page = 0, size = 5, direction = Direction.DESC) Pageable pageable) {
		return productService.confirm(pageable);
	}

	@GetMapping("/list") // 일반 - 제품목록 list(상태 : APPROVE)(paging limit 5 내림차순)
	public List<ProductDto> list(String status,
		@PageableDefault(page = 0, size = 5, direction = Direction.DESC) Pageable pageable) {
		return productService.list(pageable);
	}

	@GetMapping("/lists") // 페이지 없는 전체 목록
	public List<ProductDto> lists(){
		return productService.productList();
	}

	@GetMapping("/popular") // 일반 - 인기제품 list(조회수순 / limit 5 / 상태 : APPROVE)
	public List<ProductDto> popularList() {
		return productService.popularList();
	}

	@GetMapping("/bestLike") // 일반 - 좋아요 Best(좋아요순 / limit 8 / 상태 : APPROVE)
	public List<ProductDto> bestLikeList(){
		return productService.bestLikeList();
	}

	@GetMapping("/{product_Seq}") // 제품 상세 목록 + 조회수 증가
	public ProductDto productDetail(ProductDto parameter) {
		return productService.detail(parameter.getProduct_Seq());
	}

	@PostMapping("/update") // admin - 제품 수정
	public boolean update(@RequestBody ProductDto parameter) {

		return productService.update(parameter);

	}

	@PostMapping("/delete") // admin - 제품 삭제
	public boolean delete(@RequestBody ProductDto parameter) {

		return productService.delete(parameter.getProduct_Seq());
	}


}
