package com.project.devgram.oauth2.token;


import com.project.devgram.entity.User;
import com.project.devgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        String token =((HttpServletRequest)request).getHeader("Authentication");

        log.info("토큰 유무: {}", token);
        if (token != null && tokenService.validateToken(token)) {

            String username = tokenService.getUid(token);

            //subject가 username 받도록 함.
          Optional<User> optionalUsernames = userRepository.findByUsername(username);
          if(optionalUsernames.isPresent()){

              User user = optionalUsernames.get();
              User users= User.builder()
                      .username(user.getUsername())
                      .email(user.getEmail())
                      .providerId(user.getProviderId())
                      .role(user.getRole())
                      .build();

              Authentication auth = getAuthentication(users);
              log.info("Authentication : {} ",auth);
              SecurityContextHolder.getContext().setAuthentication(auth);

          }

        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(User member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

