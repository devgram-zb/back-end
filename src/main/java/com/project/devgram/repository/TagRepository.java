package com.project.devgram.repository;

import com.project.devgram.entity.Tag;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCase(String name);

    // like문 자동완성
    // Page<Tag> findByNameContainsIgnoreCase(String name, Pageable pageable);

    // full text index 자동완성, 사용 횟수 높은 순 정렬, 5개 반환(pageable)
    @Query(value = "SELECT * FROM tag where MATCH(name) AGAINST (:searchTag IN BOOLEAN MODE) ORDER BY use_count desc", nativeQuery = true)
    Page<Tag> searchByFti(@Param("searchTag") String searchTag, Pageable pageable);
}
