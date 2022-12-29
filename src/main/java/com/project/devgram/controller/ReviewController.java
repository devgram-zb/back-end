package com.project.devgram.controller;

import com.project.devgram.dto.ReviewAccuseDto;
import com.project.devgram.dto.ReviewDto;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.ReviewAccuseService;
import com.project.devgram.service.ReviewService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewAccuseService reviewAccuseService;
	private final TokenService tokenService;
	@PostMapping// 리뷰 등록
	public boolean write(@RequestBody ReviewDto parameter, HttpServletRequest request){
		parameter.setUsername(tokenService.getUsername(request.getHeader("Authentication")));
		return  reviewService.ReviewWrite(parameter);
	}
	@GetMapping("/admin") // 전체 리뷰(혹시 모를 관리자용..!)
	public List<ReviewDto> adminList(@PageableDefault(page = 0, size = 5, direction = Direction.DESC) Pageable pageable) {
		return reviewService.adminList(pageable);
	}
	@GetMapping("/list")// seq에 따른 목록
	public List<ReviewDto> list(@RequestParam Long productSeq){
		return reviewService.Reviewlist(productSeq);
	}

	@PostMapping("/update") // 리뷰 수정
	public boolean reviewUpdate(@RequestBody ReviewDto parameter){
		return reviewService.updateReview(parameter);
	}

	@PostMapping("/delete") // 리뷰 삭제
	public boolean reviewDelete(@RequestBody ReviewDto parameter){
		return reviewService.deleteReview(parameter);
	}

	@PostMapping("/accuse") // 리뷰 신고
	public ReviewAccuseDto reviewAccuse(@RequestBody ReviewAccuseDto parameter, HttpServletRequest request){
		parameter.setUsername(tokenService.getUsername(request.getHeader("Authentication")));
		return reviewAccuseService.reviewAccuse(parameter);
	}

	@GetMapping("/accuse/list/admin") // 리뷰 신고 list(관리자)
	public List<ReviewDto> AccuseReviewList(){
		return reviewAccuseService.accuseReviewList();
	}

	@PostMapping("/accuse/statusUpdate/admin") // 리뷰 status 변경(관리자)
	public boolean reviewStatusUpdate(@RequestBody ReviewDto parameter){
		return reviewAccuseService.updateReviewStatus(parameter.getReviewSeq(), parameter);
	}

	@GetMapping("/accuse/detail/admin") // reviewSeq에 따른 신고 목록 조회
	public List<ReviewAccuseDto> AccuseReviewDetail(@RequestParam Long reviewSeq){
		return reviewAccuseService.accuseReviewDetail(reviewSeq);
	}
}
