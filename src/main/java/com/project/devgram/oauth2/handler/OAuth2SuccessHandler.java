package com.project.devgram.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.devgram.dto.UserDto;
import com.project.devgram.dto.UserRequestMapper;
import com.project.devgram.oauth2.token.Token;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


private final TokenService tokenService;
private final UserRequestMapper mapper;

    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User  = (OAuth2User) authentication.getPrincipal();
        UserDto userDto= mapper.toDto(oAuth2User);
        log.info("userDto {}",userDto.getUsername());

        //토큰 생성
        Token token = tokenService.generateToken(userDto.getUsername(),"ROLE_USER");

        log.info("토큰 {} ",token);


        response.addHeader("Authentication", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        writeTokenResponse(response,token);

    }


    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {

        response.addHeader("Authentication", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
