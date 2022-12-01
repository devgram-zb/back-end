package com.project.devgram.controller;

import com.project.devgram.dto.UserDto;
import com.project.devgram.oauth2.exception.TokenParsingException;
import com.project.devgram.oauth2.response.UserResponse;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


private final TokenService tokenService;

private final UserService userService;


    @GetMapping("/api/loginForm")
    public  String login(){

        return "loginForm";
    }

    @PostMapping("/api/logout")
    public String logout(){
        return "redirect:/";
    }


    @GetMapping("/api/user")
    @ResponseBody
    public ResponseEntity getUserDetails(HttpServletRequest request) throws TokenParsingException {


        String token= request.getHeader("Authentication");
        log.info("token {}",token);

        String username = usernameMaker(token);
        log.info("username: {} ",username);

        UserDto users = userService.getUserDetails(username);

        return ResponseEntity.ok(users);

    }


    @PutMapping("/api/user")
    @ResponseBody
    public ResponseEntity updateUserDetails(HttpServletRequest request,@RequestBody UserDto dto) throws TokenParsingException {


        String token= request.getHeader("Authentication");
        log.info("token {}",token);
        dto.setUsername(usernameMaker(token));

        UserResponse users = userService.updateUserDetails(dto);

        return ResponseEntity.ok(users);
    }

    private String usernameMaker(String username) throws TokenParsingException {
        return tokenService.getUsername(username);
    }

}
