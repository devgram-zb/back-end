package com.project.devgram.controller;


import com.project.devgram.dto.CommonDto;
import com.project.devgram.dto.TokenDto;
import com.project.devgram.exception.errorcode.TokenErrorCode;
import com.project.devgram.oauth2.exception.ForbiddenException;
import com.project.devgram.oauth2.redis.RedisService;
import com.project.devgram.oauth2.token.Token;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.type.ROLE;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(value = "/api/token")
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

            String username = tokenService.getUsername(token);
            String rtkRedis = redisService.getRefreshToken(username);

            if (Objects.isNull(rtkRedis)) {
                throw new ForbiddenException("인증정보가 만료되었습니다. ");
            }

            Token newToken = tokenService.generateToken(username, String.valueOf(ROLE.ROLE_USER));

            response.addHeader("Authentication", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "refresh success";
        }

        throw new RuntimeException(String.valueOf(TokenErrorCode.INVALIDATED_TOKEN));
    }

    @GetMapping
    public CommonDto<Token> getTokens(@RequestBody @Valid TokenDto dto, BindingResult bindingResult){

            String username = "github"+dto.getId();

            Token tokens = tokenService.generateToken(username,RoleMaker(dto.getId()));

            return new CommonDto<>(HttpStatus.OK.value(), tokens);

    }

    @GetMapping("/role")
    public CommonDto<String> getRoles(HttpServletRequest request){

        String token = request.getHeader("Authentication");

       String grant =  tokenService.getTokenGrant(token);

       return new CommonDto<>(HttpStatus.OK.value(),grant);
    }
    
    

    //ADMIN ROLE 구분
    private static String RoleMaker(String username){
        if(username.equals("ADMIN")){
           return String.valueOf(ROLE.ROLE_ADMIN);
        }else {
            return String.valueOf(ROLE.ROLE_USER);
        }
    }


}
