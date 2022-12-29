package com.project.devgram.controller;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryContoller {

	private final CategoryService categoryService;

	@GetMapping("/admin")
	public List<CategoryDto> list() {

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
