package com.project.devgram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.project.devgram.dto.TagDto;
import com.project.devgram.entity.Tag;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.TagErrorCode;
import com.project.devgram.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Test
    @DisplayName("태그 목록 조회 - 성공")
    void getTagList_success() {
        // given
        List<Tag> tagList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tagList.add(Tag.builder()
                .name("name" + i)
                .useCount((long) i)
                .build());
        }

        given(tagRepository.findAll()).willReturn(tagList);

        // when
        List<TagDto> result = tagService.getTagList();

        // then
        assertEquals(result.size(), tagList.size());
    }

    @Test
    @DisplayName("태그 목록 조회 - 실패 - 태그 존재 X")
    void getTagList_fail_notExistent() {
        // given
        List<Tag> tagList = new ArrayList<>();

        given(tagRepository.findAll()).willReturn(tagList);

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> tagService.getTagList());

        // then
        assertEquals(devGramException.getErrorCode(), TagErrorCode.NOT_EXISTENT_TAG);
    }

    @Test
    @DisplayName("특정 태그 삭제 - 성공")
    void deleteTag_success() {
        // given
        Tag tag = Tag.builder()
            .tagSeq(1L)
            .name("name")
            .useCount(1L)
            .build();

        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));

        // when
        String result = tagService.deleteTag(1L);

        // then
        verify(tagRepository).delete(tag);
        assertEquals(result, "태그 삭제 완료");
    }

    @Test
    @DisplayName("특정 태그 삭제 - 실패 - 태그 존재 X")
    void deleteTag_fail_notExistent() {
        // given
        given(tagRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        DevGramException devGramException = assertThrows(DevGramException.class,
            () -> tagService.deleteTag(anyLong()));

        // then

        assertEquals(devGramException.getErrorCode(), TagErrorCode.NOT_EXISTENT_TAG);
    }

}