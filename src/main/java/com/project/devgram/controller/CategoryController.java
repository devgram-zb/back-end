package com.project.devgram.controller;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/admin")
	public List<CategoryDto> list() {

		return categoryService.list();
	}

	@GetMapping
	public List<CategoryDto> getCateList() {

		return categoryService.list();
	}

	@PostMapping("/admin")
	public boolean add(@RequestBody CategoryDto parameter) {

		return categoryService.add(parameter);
	}

	@PostMapping("/update/admin")
	public boolean update(@RequestBody CategoryDto parameter) {

		return categoryService.update(parameter);
	}

	@PostMapping("/delete/admin")
	public boolean del(@RequestBody CategoryDto parameter) {

		return categoryService.del(parameter.getCategory_Seq());

	}
}
