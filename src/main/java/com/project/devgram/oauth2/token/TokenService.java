package com.project.devgram.oauth2.token;

import com.project.devgram.oauth2.exception.TokenParsingException;
import com.project.devgram.oauth2.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {


    @Value("${jwt.secretKey}")
    private String secretKey;

    private final RedisService redisService;

    @PostConstruct // 빈등록 후 초기화를 시켜주는 어노테이션
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public Token generateToken(String username, String role) {
        long tokenPeriod = 1000L * 60L * 10L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;


        String[] tokenCheck = {"ATK", "RTK"};

        String token = typoToken(username, role, tokenCheck[0], tokenPeriod);
        String refreshToken = typoToken(username, role, tokenCheck[1], refreshPeriod);

        redisService.createRefresh(username, refreshToken, tokenCheck[1], refreshPeriod);
        return Token.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }


    private String typoToken(String username, String role, String type, long period) {

        Date now = new Date();

        Claims claims = Jwts.claims()
                .setId(username)
                .setSubject(type);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("jwt", "jwt")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + period))
                .signWith(getSignKey(secretKey), SignatureAlgorithm.HS256)
                .compact();


    }



    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex) {
            log.error("JWT RefreshToken is empty");
        }
        return false;
    }

    public String getUsername(final String token) throws TokenParsingException {

        final String payloadJWT = token.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();

        final String payload = new String(decoder.decode(payloadJWT));
        BasicJsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(payload);

        if (!jsonArray.containsKey("jti") || !jsonArray.get("sub").toString().equals("ATK")) {

            throw new TokenParsingException("유효하지 않은 AccessToken 입니다");
        }
        log.info("토큰 파싱 확인: " + jsonArray.get("jti").toString());

        return jsonArray.get("jti").toString();

    }

    private Key getSignKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUname(String token) {

        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody().getId();
    }

    public String getTokenCheck(String token) {

        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody().getSubject();
    }



}
