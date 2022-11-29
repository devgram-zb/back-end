package com.project.devgram.oauth.auth;

import com.project.devgram.oauth.entity.User;
import com.project.devgram.oauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl"/login
// 요청이 오면 자동으로 UserDetailService 타입으로 IOC 되어 있는 loadUSERbYuSERNAME 함수가 실행

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity!= null){
            return new PrincipalDetails(userEntity);
        }
        return null;

    }
}
