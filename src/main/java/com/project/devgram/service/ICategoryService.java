package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import java.util.List;

public interface ICategoryService {

	List<CategoryDto> list();

	boolean add(CategoryDto parameter);
	boolean update(CategoryDto parameter);
	boolean del(long id);
}
