package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.entity.Category;
import com.project.devgram.repository.ICategoryRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryServiceImplTest {

	@Mock
	private ICategoryService categoryService;
	@Mock
	private ICategoryRepository repository;

	@Test
	void CategoryList(){

		//given(변수 설정)
		Category category = Category.builder().
		category_seq(1L)
			.name("카테고리목록테스트용")
			.order(1)
			.build();

		// when(실제 액션)

		List<CategoryDto> list = categoryService.list();

		//then

		assertNotNull(repository);
	}
}