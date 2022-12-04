package com.project.devgram.oauth2.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {

    private String token;
    private String refreshToken;

    public Token(String token, String refreshToken){
        this.token=token;
        this.refreshToken=refreshToken;
    }
}