package com.project.devgram.service;

import com.project.devgram.dto.TagDto;
import com.project.devgram.entity.Tag;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.TagErrorCode;
import com.project.devgram.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /*
     * 태그 등록
     */
    public void addTag(List<String> tagNames) {

        if (tagNames.isEmpty()) {
            return;
        }

        for (String tagName : tagNames) {
            Optional<Tag> optionalTag = tagRepository.findByNameIgnoreCase(tagName);

            if (optionalTag.isEmpty()) {
                Tag tag = Tag.builder()
                    .name(tagName)
                    .useCount(1L)
                    .build();

                tagRepository.save(tag);
            } else {
                Tag tag = optionalTag.get();
                tag.setUseCount(tag.getUseCount() + 1L);

                tagRepository.save(tag);
            }
        }
    }

    /*
     * 태그 전체 목록 조회
     */
    public List<TagDto> getTagList() {
        List<Tag> tagList = tagRepository.findAll();

        if (tagList.isEmpty()) {
            throw new DevGramException(TagErrorCode.NOT_EXISTENT_TAG);
        }

        return TagDto.fromList(tagList);
    }

    /*
     * 특정 태그 삭제
     */
    public String deleteTag(Long tagSeq) {

        tagRepository.delete(
            tagRepository.findById(tagSeq)
                .orElseThrow(() -> new DevGramException(TagErrorCode.NOT_EXISTENT_TAG))
        );

        return "태그 삭제 완료";
    }

    /*
     * 태그 자동 완성
     */
    public List<TagDto> autoCompleteTag(String searchTag) {
        Pageable limit = PageRequest.of(0, 5);

        Page<Tag> tagList = tagRepository.searchByFti(searchTag, limit);

        if (tagList.isEmpty()) {
            throw new DevGramException(TagErrorCode.NOT_CORRESPOND_TAG);
        }

        return TagDto.fromList(tagList);
    }

    /*
     * 인기 태그 조회(8개, 태그 사용 순)
     */
    public List<TagDto> getPopularTagList() {

        List<Tag> tagList = tagRepository.findTop8ByOrderByUseCountDesc();

        if (tagList.isEmpty()) {
            throw new DevGramException(TagErrorCode.NOT_EXISTENT_TAG);
        }

        return TagDto.fromList(tagList);
    }
}
