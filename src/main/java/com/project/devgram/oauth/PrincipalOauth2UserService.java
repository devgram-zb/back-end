package com.project.devgram.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Override //후처리 용 함수
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       log.info("유저 : "+userRequest);
       log.info("어떤 RESOURCE 서버: "+userRequest.getClientRegistration());
       log.info("access token : {} ",userRequest.getAccessToken().getTokenValue());
       log.info("그외 나머지 :"+ userRequest.getClientRegistration());
       log.info("프로필 data:   "+super.loadUser(userRequest).getAttributes());

       OAuth2User Oauth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
