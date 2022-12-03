package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.entity.Category;
import com.project.devgram.repository.ICategoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceImplTest {

	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private ICategoryRepository repository;

	@Test
	@DisplayName("Category List Test")
	void CategoryList() {

		List<Category> categories = repository.findAll();

		if (categories.isEmpty()) {
			System.out.println("List is Empty");
		} else {
			System.out.println("List is not Empty");
		}
	}

	@Test
	@DisplayName("Category Add Test")
	void add() {

		CategoryDto parameter = CategoryDto.builder()
			.category_Seq(1L)
			.name("Test1")
			.color("White")
			.build();

		boolean result = categoryService.add(parameter);

		assertTrue(result);
		if (result == true) {
			System.out.println("add Test Success");
		}


	}


	@Test
	@DisplayName("Category Delete Test")
	void del() {
		CategoryDto parameter = CategoryDto.builder()
			.category_Seq(1L).build();
		boolean result = categoryService.del(parameter.getCategory_Seq());
		assertTrue(result);
		if (result == true) {
			System.out.println("delete Test Success");
		}
	}
}