package com.project.devgram.repository;

import com.project.devgram.entity.CommentAccuse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentAccuseRepository extends JpaRepository<CommentAccuse, Long> {
    List<CommentAccuse> findByComment_CommentSeq(Long commentSeq, Sort sort);

    Optional<CommentAccuse> findTop1ByComment_CommentSeq(Long commentSeq, Sort sort);
}
