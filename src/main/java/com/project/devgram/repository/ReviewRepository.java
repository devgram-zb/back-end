package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review findByUsersAndProduct(Users users, Product product);


}