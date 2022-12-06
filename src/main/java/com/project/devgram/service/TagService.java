package com.project.devgram.service;

import com.project.devgram.dto.TagDto;
import com.project.devgram.entity.Tag;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.TagErrorCode;
import com.project.devgram.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

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
}
