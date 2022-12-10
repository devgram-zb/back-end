package com.project.devgram.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devgram.entity.User;
import com.project.devgram.type.ROLE;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@RequiredArgsConstructor
public class UserDto {

    private Long userSeq;
    private String email;
    private String password;
    private String username;

    private ROLE role;

    private String providerId;
    private String annual;
    private String job;

    private int followCount;
    private int followerCount;


    @Builder
    public UserDto(Long userSeq, String email, String password, String username,
                   ROLE role, String providerId, String annual, String job, int followCount, int followerCount){

        this.username = username;
        this.userSeq = userSeq;
        this. email = email;
        this.job = job;
        this.role= role;
        this.followCount=followCount;
        this.annual = annual;
        this.providerId= providerId;
        this.password = password;
        this.followerCount= followerCount;
    }

    public static List<UserDto> of(List<User> userList) {

        if(userList.size() > 0){
            List<UserDto> followerList = new ArrayList<>();
            for (User u: userList) {
                UserDto dto = UserDto.builder()
                        .userSeq(u.getUserSeq())
                        .username(u.getUsername())
                        .annual(u.getAnnual())
                        .job(u.getJob())
                        .followerCount(u.getFollowerCount())
                        .followerCount(u.getFollowerCount())
                        .build();
                followerList.add(dto);
            }
            return followerList;
        }
        log.error("followerList error");
        return null;

    }


    @SuppressWarnings("unchecked")
    @JsonProperty("user") // form Name
    private void unpackNested(Map<String,Object> user) {
        this.userSeq =Long.parseLong(String.valueOf(user.get("userSeq")));
        this.username = String.valueOf(user.get("username"));
        this.password= String.valueOf(user.get("password"));
        this.annual=String.valueOf(user.get("annual"));
        this.job= String.valueOf(user.get("job"));
    }


    public void toDto(String id) {
        this.providerId = id;
        this.username = "github" + providerId;

    }

}
