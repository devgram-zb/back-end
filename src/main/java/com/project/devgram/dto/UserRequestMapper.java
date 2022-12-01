package com.project.devgram.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public UserDto toDto(OAuth2User oAuth2User) {
        String providerId =String.valueOf((Object) oAuth2User.getAttribute("id"));
        String username ="github"+providerId;

        return UserDto.builder()
                .username(username)
                .build();
    }



}