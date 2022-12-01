package com.project.devgram.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.devgram.type.ROLE;
import lombok.*;

import java.util.Map;

@Data
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
    public UserDto(Long userSeq, String email,String password,String username,
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


    @SuppressWarnings("unchecked")
    @JsonProperty("user") // form Name
    private void unpackNested(Map<String,Object> user) {
        this.userSeq =Long.parseLong(String.valueOf(user.get("userSeq")));
        this.username = String.valueOf(user.get("username"));
        this.password= String.valueOf(user.get("password"));
        this.annual=String.valueOf(user.get("annual"));
        this.job= String.valueOf(user.get("job"));
    }

    /*  Map<String,String> apart = (Map<String,String>)address.get("apart");
        this.apartName = apart.get("apartName");*/
}
