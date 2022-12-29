package com.project.devgram.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.devgram.type.ProductCode;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product implements ProductCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productSeq;
	private String title;
	private String content;
	private Integer hits;
	private double rating;
	private Integer likeCount;
	private double price;
	private String status;
	private int reviewCount;
	private double totalRating;

	private String imageUrl;

	@Builder.Default
	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<Review> reviewList = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<ProductLike> productLikes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_Seq")
	@JsonBackReference
	private Category category;

	public void addReview(Review review) {
		this.reviewList.add(review);
	}
}
