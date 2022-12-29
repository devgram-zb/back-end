package com.project.devgram.dto;


import com.project.devgram.entity.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

	private Long category_Seq;
	private String name;
	private String color;

	public static List<CategoryDto> of(List<Category> categories){
		if (categories != null){
			List<CategoryDto> categoryList = new ArrayList<>();
			for (Category x : categories){
				categoryList.add(of(x));
			}
			return categoryList;
		}
		return null;
	}
	public static CategoryDto of(Category category){
		return CategoryDto.builder()
			.category_Seq(category.getCategory_Seq())
			.name(category.getName())
			.color(category.getColor())
			.build();
	}

}
