package com.project.devgram.service;

import com.project.devgram.entity.User;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ROLE;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;





    @Test
    @DisplayName("회원가입 test")
    void userRegister(){
    //given
        User user = User.builder()
                .userSeq(1L)
                .username("github123124")
                .role(ROLE.valueOf("ROLE_USER"))
                .build();
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);


    //when
        userRepository.save(user);

    //then
         verify(userRepository,times(1)).save(captor.capture());

    }

    @Test
    @DisplayName("회원정보조회")
    void getUserDetails(){
        //given
        User user = User.builder()
                .userSeq(1L)
                .username("github123124")
                .role(ROLE.valueOf("ROLE_USER"))
                .build();

        given(userRepository.findByUsername(anyString()))
                .willReturn(Optional.of(user));


        //when
        userRepository.save(user);
        Optional<User> optionalProductDetail = userRepository.findByUsername("github123124");

        //then
        assertEquals("github123124",optionalProductDetail.get().getUsername());
        System.out.println("username: "+optionalProductDetail.get().getUsername());
    }


}