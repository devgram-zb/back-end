package com.project.devgram.oauth2.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String code;
    private String description;

    public UserResponse(String code,String description) {
        this.description=description;
        this.code=code;
    }

}
