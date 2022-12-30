package com.project.devgram.config;


import com.project.devgram.oauth2.principal.PrincipalDetailsService;
import com.project.devgram.oauth2.token.JwtAuthFilter;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;


    private final CorsConfig corsConfig;
    private final TokenService tokenService;
    private final UserRepository userRepository;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/user/**").access("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
                .antMatchers("**/admin").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and().logout().logoutSuccessUrl("/");

        /* 관리자는 회원페이지 조회가 가능, 유저는 ADMIN 페이지 조회 불가능*/

       /*         .and()
                .oauth2Login().defaultSuccessUrl("/") // loginForm 삭제 1212
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        */

        http.addFilterBefore(new JwtAuthFilter(authenticationManager((AuthenticationConfiguration) authenticationManager),tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .mvcMatchers("**/favicon.ico","/cdn**","/maxcdn**","/jquery**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    //@crossOrigin은 인증이 필요한 요청을 무시
}
