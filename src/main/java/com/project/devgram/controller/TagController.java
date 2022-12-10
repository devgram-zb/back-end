package com.project.devgram.controller;

import com.project.devgram.dto.TagDto;
import com.project.devgram.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tags")
public class TagController {

    private final TagService tagService;

    /*
     * 전체 태그 목록 조회
     */
    @GetMapping
    public List<TagDto> getTagList() {
        return tagService.getTagList();
    }

    /*
     * 특정 태그 삭제
     */
    @DeleteMapping
    public String deleteTag(@RequestParam Long tagSeq) {
        return tagService.deleteTag(tagSeq);
    }

    /*
     * 태그 자동 완성
     */
    @GetMapping("/autocomplete")
    public List<TagDto> autocompleteTag(@RequestParam String name) {
        return tagService.autoCompleteTag(name);
    }
}
