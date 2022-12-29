package com.project.devgram.dto;

import com.project.devgram.entity.ProductLike;
import com.project.devgram.entity.Users;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLikeDto {
	@NotNull
	private String username;
	@NotNull
	private Long productSeq;
	public static List<ProductLikeDto> of(List<ProductLike> productLikes) {
		if (productLikes != null) {
			List<ProductLikeDto> productLikeList = new ArrayList<>();
			for (ProductLike x : productLikes) {
				productLikeList.add(of(x));
			}
			return productLikeList;
		}
		return null;
	}
	public static ProductLikeDto of(ProductLike productLike){
		return ProductLikeDto.builder()
			.productSeq(productLike.getProduct().getProductSeq())
			.username(productLike.getUsername())
			.build();
	}
}
