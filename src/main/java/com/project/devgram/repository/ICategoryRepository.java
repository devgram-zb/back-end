package com.project.devgram.repository;

import com.project.devgram.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

}
