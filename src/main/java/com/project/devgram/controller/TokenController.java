package com.project.devgram.controller;


import com.project.devgram.oauth2.exception.ForbiddenException;
import com.project.devgram.oauth2.redis.RedisService;
import com.project.devgram.oauth2.token.Token;
import com.project.devgram.oauth2.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(value = "/api/token/")
public class TokenController {

    private final TokenService tokenService;
    private final RedisService redisService;

    @GetMapping("/expired")
    public String auth() {
        throw new RuntimeException();
    }

    // Access token 재발급을 위한 RefreshToken;
    @GetMapping("/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {


        String token = request.getHeader("Refresh");
        if (token != null && tokenService.validateToken(token) && tokenService.getTokenCheck(token).equals("RTK")) {

            String username = tokenService.getUname(token);
            String rtkRedis = redisService.getRefreshToken(username);

            log.info("redis :  {} ", rtkRedis);
            if (Objects.isNull(rtkRedis)) {
                throw new ForbiddenException("인증정보가 만료되었습니다. ");
            }

            Token newToken = tokenService.generateToken(username, "USER");

            response.addHeader("Authentication", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "refresh success";
        }

        throw new RuntimeException();
    }


}
