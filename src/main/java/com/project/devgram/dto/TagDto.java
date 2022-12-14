package com.project.devgram.dto;

import com.project.devgram.entity.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TagDto {
    private Long tagSeq;

    private String name;

    private Long useCount;

    public static TagDto from(Tag tag) {
        return new TagDto(tag.getTagSeq(), tag.getName(), tag.getUseCount());
    }

    public static List<TagDto> fromList(List<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>();

        for (Tag tag: tagList) {
            tagDtoList.add(from(tag));
        }

        return tagDtoList;
    }

    public static List<TagDto> fromList(Page<Tag> tagList) {
        List<TagDto> tagDtoList = new ArrayList<>();

        for (Tag tag: tagList) {
            tagDtoList.add(from(tag));
        }

        return tagDtoList;
    }
}
