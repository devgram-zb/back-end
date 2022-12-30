package com.project.devgram.oauth2.token;


import com.project.devgram.entity.Users;
import com.project.devgram.oauth2.principal.PrincipalDetails;
import com.project.devgram.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;
    //  private final PrincipalDetailsService principalDetailsService;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String accessToken = ((HttpServletRequest) request).getHeader("Authentication");
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if (accessToken != null && tokenService.validateToken(accessToken)) {
            try {
                String username = tokenService.getUsername(accessToken);
                log.info("username : {}", username);

                if(isLoginUrlCheck(accessToken,requestURI)) {

                    Optional<Users> user=  userRepository.findByUsername(username);

                    PrincipalDetails principalDetails = new PrincipalDetails(user.get());
                    Authentication auth = new UsernamePasswordAuthenticationToken(principalDetails, "", principalDetails.getAuthorities());
                    log.info("Authentication : {} ", auth);
                    log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {},authorities {}", auth.getName(), requestURI,auth.getAuthorities().toString());
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }

        }

        chain.doFilter(request, response);
    }

    private boolean isLoginUrlCheck(String accessToken,String requestURI){
        String tokenCheck = tokenService.getTokenCheck(accessToken);
        boolean blackList = tokenService.getListCheck(accessToken);
        String grant = tokenService.getTokenGrant(accessToken);

        log.info("what's your grant: {}", grant);
        // 로그인 여부 판단
        if(blackList){
            throw new JwtException("토큰을 확인하세요");
        }

        //만료 된 토큰 판단
        if (tokenCheck.equals("RTK") && !requestURI.equals("/api/token/refresh")) {

            throw new JwtException("토큰을 확인하세요");
        }

        return true;
    }


}
