//package com.project.devgram.controller;
//
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project.devgram.dto.BoardDto;
//import com.project.devgram.dto.RegisterBoard;
//import com.project.devgram.service.BoardService;
//import com.project.devgram.service.TagService;
//import com.project.devgram.type.status.Status;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
//@WebMvcTest(BoardController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//class BoardControllerTest {
//	@MockBean
//	private BoardService boardService;
//
//	@MockBean
//	private TagService tagService;
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//	//TODO springSecurity 완료시 해당 테스트 코드에도 반영
//	@Test
//	@DisplayName("보드_등록_성공")
//	void registerBoard_Success() throws Exception {
//		given(boardService.registerBoard(anyString(),anyString()))
//			.willReturn(BoardDto.builder()
//				.title("아무제목")
//				.content("아무내용")
//				.status(Status.NORMAL)
//				.likeCount(0)
//				.build());
//
//		List<String> values = Arrays.asList("태그1", "태그2", "태그3");
//		MultiValueMap<String, String> tagNames = new LinkedMultiValueMap<>();
//		tagNames.addAll("tagNames", values);
//
//		mockMvc.perform(post("/api/boards")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(objectMapper.writeValueAsString(
//				new RegisterBoard.Request("ㅁㅁㅁ","ㅁㅁㅁ")
//			))
//				.params(tagNames))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.title").value("아무제목"))
//			.andExpect(jsonPath("$.content").value("아무내용"))
//			.andExpect(jsonPath("$.status").value(Status.NORMAL.getValue()))
//			.andExpect(jsonPath("$.likeCount").value(0));
//	}
//
//	//TODO springSecurity 완료시 해당 테스트 코드에도 반영
//	@Test
//	@DisplayName("보드_등록_실패_제목200자이상")
//	void registerBoard_Fail_OverMaxSize() throws Exception {
//		given(boardService.registerBoard(anyString(),anyString()))
//			.willReturn(BoardDto.builder()
//				.title("아무제목")
//				.content("아무내용")
//				.status(Status.NORMAL)
//				.likeCount(0)
//				.build());
//
//		List<String> values = Arrays.asList("태그1", "태그2", "태그3");
//		MultiValueMap<String, String> tagNames = new LinkedMultiValueMap<>();
//		tagNames.addAll("tagNames", values);
//
//		mockMvc.perform(post("/api/boards")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(
//					new RegisterBoard.Request("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ"
//						,"ㅁㅁㅁ")
//				))
//				.params(tagNames))
//			.andExpect(status().is4xxClientError())
//			.andExpect(result -> assertTrue(result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)));
//	}
//}