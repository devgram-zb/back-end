package com.project.devgram.repository;

import com.project.devgram.dto.BoardLikeDto;
import com.project.devgram.dto.SearchBoardLike.Request;
import java.util.List;

public interface CustomBoardLikeRepository {
	List<BoardLikeDto> findBy(Request request);
}
