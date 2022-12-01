package com.project.devgram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {



    @GetMapping({"/",""})
    public String index() throws IOException {

        return "index";
    }

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
    public ResponseEntity getUserDetail(HttpServletRequest request){


      String token= request.getHeader("Authentication");
      log.info("token {}",token);


        return ResponseEntity.ok(null);

    }

}
