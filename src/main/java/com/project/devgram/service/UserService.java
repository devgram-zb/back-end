package com.project.devgram.service;

import com.project.devgram.dto.UserDto;
import com.project.devgram.entity.User;
import com.project.devgram.oauth2.response.UserResponse;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ResponseEnum;
import com.project.devgram.util.passUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDto getUserDetails(String username){

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 정보가 없습니다.")));

        if(userOptional.isPresent()){

            User user = userOptional.get();

            return UserDto.builder()
                    .userSeq(user.getUserSeq())
                    .job(user.getJob())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .annual(user.getAnnual())
                    .role(user.getRole())
                    .followCount(user.getFollowCount())
                    .followerCount(user.getFollowerCount())
                    .password(user.getPassword())
                    .providerId(user.getProviderId())
                    .build();
        }
        log.error("getUserDetails fail");
        return null;

    }


    public UserResponse updateUserDetails(UserDto dto) {
        log.info("dtos {}",dto);

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 정보가 없습니다.")));

        String encPassword = passUtil.encPassword(dto.getPassword());

        if (userOptional.isPresent()) {

            User user = userOptional.get();
            user.setPassword(encPassword);
            user.setJob(dto.getJob());
            user.setAnnual(dto.getAnnual());
            user.setUserSeq(dto.getUserSeq());


            userRepository.save(user);
            return new UserResponse(String.valueOf(ResponseEnum.success),"update complete");
        }

        log.error("fail");
        return new UserResponse(String.valueOf(ResponseEnum.fail),"update fail");
    }

}
