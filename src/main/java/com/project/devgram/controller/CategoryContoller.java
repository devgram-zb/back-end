package com.project.devgram.controller;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.service.ICategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryContoller {

	private final ICategoryService categoryService;

	@GetMapping
	public List<CategoryDto> list() {

		return categoryService.list();
	}

	@PostMapping
	public boolean add(@RequestBody CategoryDto parameter) {

		return categoryService.add(parameter);
	}

	@PostMapping("/update")
	public boolean update(@RequestBody CategoryDto parameter) {

		return categoryService.update(parameter);
	}

	@PostMapping("/delete")
	public boolean del(@RequestBody CategoryDto parameter) {

		return categoryService.del(parameter.getCategory_Seq());

	}
}
