package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	boolean write(ProductDto parameter, MultipartFile file) throws IOException;

	List<ProductDto> confirm(Pageable pageable); // admin 제품관리

	List<ProductDto> list(Pageable pageable); // 전체 list(Approve)

	List<ProductDto> productList(); // 페이징 없는 전체 리뷰 List(New!)

	boolean update(ProductDto parameter); // product 업데이트

	boolean delete(long id); // product 삭제

	List<ProductDto> popularList(); // list(Approve) 인기순 4

	List<ProductDto> bestLikeList();

	ProductDto detail(long id); // product Detail + 조회수증가


}
