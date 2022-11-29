package com.project.devgram.oauth.auth;

import com.project.devgram.oauth.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

//시큐리티가 /login 주소 요청을 낚아채서 로그인을 진행
// 로그인을 진행이 완료가 되면 시큐리티 session을 만든다.
//security ContextHolder라는 키값에 세션 값 저장.
//오브젝트가 정해져 있음. Authentication 타입 객체 여야함. 안에 유저 정보가 담김
// security  Session => Authentication => UserDetails
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    //Authentication 에 PrincipalDetails 형식으로 넣어 놓으면 casting 가능하지
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    private User user;//콤포지션

    public PrincipalDetails(User user){
        this.user = user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
