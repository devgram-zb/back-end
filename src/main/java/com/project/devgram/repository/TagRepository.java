package com.project.devgram.repository;

import com.project.devgram.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Page<Tag> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
