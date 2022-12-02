package com.project.devgram.oauth2.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TokenServiceTest {



    @Test
    @DisplayName("payload값 확인")
    void jwts_test() {
        String payload = "eyJzdWIiOiJnaXRodWI3MTMwMzQ0OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE2Njk4NjU1NzksImV4cCI6MTY2OTg2NjE3OX0";

        Base64.Decoder decoder = Base64.getUrlDecoder();
        final String payloadString = new String(decoder.decode(payload));

        System.out.println(payloadString);
    }


    @Test
    @DisplayName("payload 특정값 확인")
    void jwts_test2() {
        String payload = "eyJzdWIiOiJnaXRodWI3MTMwMzQ0OCIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE2Njk4NjU1NzksImV4cCI6MTY2OTg2NjE3OX0";
        Base64.Decoder decoder = Base64.getUrlDecoder();

        final String payloadJWT = new String(decoder.decode(payload));
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(payloadJWT);

        assertEquals(jsonArray.get("sub"),"github71303448");
        assertEquals(jsonArray.get("role"),"ROLE_USER");
    }






}