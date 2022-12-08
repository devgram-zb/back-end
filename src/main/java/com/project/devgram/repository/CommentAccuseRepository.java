package com.project.devgram.repository;

import com.project.devgram.entity.CommentAccuse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAccuseRepository extends JpaRepository<CommentAccuse, Long> {
    Optional<List<CommentAccuse>> findByCommentSeq(Long commentSeq, Sort sort);

    Optional<CommentAccuse> findTop1ByCommentSeq(Long commentSeq, Sort sort);
}
