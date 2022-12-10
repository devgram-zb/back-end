package com.project.devgram.repository;

import com.project.devgram.entity.BoardLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> ,CustomBoardLikeRepository {
	Optional<BoardLike> findByBoard_BoardSeqAndUser_UserSeq(Long boardSeq, Long UserSeq);
}
