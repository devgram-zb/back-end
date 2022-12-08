package com.project.devgram.dto;

import com.project.devgram.entity.CommentAccuse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentAccuseDto {

    private Long commentAccuseSeq;
    private Long commentSeq;
    private String createdBy;
    private LocalDateTime createdAt;
    private String accuseReason;

    public static CommentAccuseDto from(CommentAccuse commentAccuse) {
        return CommentAccuseDto.builder()
            .commentAccuseSeq(commentAccuse.getCommentAccuseSeq())
            .commentSeq(commentAccuse.getCommentSeq())
            .createdBy(commentAccuse.getCreatedBy())
            .createdAt(commentAccuse.getCreatedAt())
            .accuseReason(commentAccuse.getAccuseReason())
            .build();
    }

    public static List<CommentAccuseDto> fromList(List<CommentAccuse> commentAccuseList) {
        List<CommentAccuseDto> commentAccuseDtoList = new ArrayList<>();

        for (CommentAccuse commentAccuse : commentAccuseList) {
            commentAccuseDtoList.add(from(commentAccuse));
        }

        return commentAccuseDtoList;
    }
}
