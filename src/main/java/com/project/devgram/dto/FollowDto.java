package com.project.devgram.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FollowDto {

    // 내 pk
    private Long userSeq;
    private String username;


    //내가 follow한 사람 정보
    private Long followingUserSeq;
    private String followingUsername;

    private String followStat;



    @SuppressWarnings("unchecked")
    @JsonProperty("FollowDto") // form Name
    private void unpackNested(Map<String,Object> follow) {
        this.followingUserSeq =Long.parseLong(String.valueOf(follow.get("followingUserSeq")));
        this.followingUsername = String.valueOf(follow.get("followingUsername"));

    }
}
