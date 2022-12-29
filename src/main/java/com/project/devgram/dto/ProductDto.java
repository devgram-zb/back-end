package com.project.devgram.dto;


import com.project.devgram.entity.Category;
import com.project.devgram.entity.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {

	private long product_Seq;
	private String title;
	private String content;
	private Integer hits;
	private double rating;
	private Integer likeCount;
	private double price;

	private String status; // 상태

	private long category_Seq; // 카테고리 id

	private int reviewCount;
	private double totalRating;

	//카테고리
	private String categoryName;
	private String categoryColor;

	private String imageUrl;



	public static List<ProductDto> of(List<Product> products) {
		if (products != null) {
			List<ProductDto> productList = new ArrayList<>();
			for (Product x : products) {
				productList.add(of(x));
			}
			return productList;
		}
		return null;
	}

	public static ProductDto of(Product product) {
		return ProductDto.builder()
			.product_Seq(product.getProductSeq())
			.title(product.getTitle())
			.content(product.getContent())
			.hits(product.getHits())
			.rating(product.getRating())
			.likeCount(product.getLikeCount())
			.price(product.getPrice())
			.status(product.getStatus())
			.category_Seq(product.getCategory().getCategory_Seq())
			.categoryColor(product.getCategory().getColor())
			.categoryName(product.getCategory().getName())
			.imageUrl(product.getImageUrl())
			.build();

	}
}
