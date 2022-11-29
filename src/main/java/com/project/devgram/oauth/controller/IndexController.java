package com.project.devgram.oauth.controller;

import com.project.devgram.oauth.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {


    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication
    , @AuthenticationPrincipal PrincipalDetails principalDetails){

        log.info("/test/Login=========================");

       log.info("userDetails : {} ",principalDetails.getUser());
        System.out.println("userDetailsss==="+principalDetails.getUser());
        return "세션 정보 확인";
    };

    @GetMapping("/test/oauth/login")
    public @ResponseBody String loginOauthTest(Authentication authentication
         , @AuthenticationPrincipal OAuth2User oauth ){

        log.info("/test/oauth/login=========================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("authentication: "+oAuth2User.getAttributes());
        log.info("getOauth Attributes: {}" ,oauth.getAttributes());

        // 위의 두개의 정보가 같다
        return "세션 정보 확인";
    };


    @GetMapping({"/",""})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }


    @GetMapping("/loginForm")
    public  String login(){
        return "loginForm";
    }



}
