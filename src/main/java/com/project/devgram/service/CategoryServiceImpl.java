package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.entity.Category;
import com.project.devgram.repository.ICategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

	private final ICategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> list() {
		List<Category> categories = categoryRepository.findAll();
		return CategoryDto.of(categories);

	}

	@Override
	public boolean add(CategoryDto parameter) {

		Category category = Category.builder()
			.name(parameter.getName())
			.color(parameter.getColor())
			.build();
		categoryRepository.save(category);
		return true;
	}

	@Override
	public boolean update(CategoryDto parameter) {

		Optional<Category> optionalCategory = categoryRepository.findById(
			parameter.getCategory_Seq());

		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			category.setName(parameter.getName());
			category.setColor(parameter.getColor());

			categoryRepository.save(category);
		}
		return true;
	}

	@Override
	public boolean del(long id) {

		categoryRepository.deleteById(id);
		return true;
	}
}
