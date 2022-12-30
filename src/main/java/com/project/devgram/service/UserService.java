package com.project.devgram.service;

import com.project.devgram.dto.UserDto;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ROLE;
import com.project.devgram.util.passUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageUploader uploader;


    public UserDto getUserDetails(String username){

        Users user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DevGramException(UserErrorCode.USER_NOT_EXIST,
                        "해당하는 유저가 없습니다."));

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


    public void updateUserDetails(UserDto dto) throws IOException {
        log.info("dtos {}",dto);
        String IMAGE_DIR = "DevUser";

        Users user =userRepository.findByUsername(dto.getUsername())
                .orElseThrow(()-> new DevGramException(UserErrorCode.USER_NOT_EXIST));

        String encPassword = passUtil.encPassword(dto.getPassword());
        String imageUrl = uploader.upload(dto.getImageFile(), IMAGE_DIR);

            user.setPassword(encPassword);
            user.setJob(dto.getJob());
            user.setAnnual(dto.getAnnual());
            user.setUserSeq(dto.getUserSeq());
            user.setRole(dto.getRole());
            user.setImageUrl(imageUrl);



            userRepository.save(user);
    }

    public String saveUserDetails(UserDto dto) {

        UUID str = UUID.randomUUID();
        String sumStr = String.valueOf(str);
        String extraWord = sumStr.substring(0,6);
        String username ="github"+dto.getId();

        log.info("username {} ",dto.getId());

        Optional<Users> userEntity = userRepository.findByUsername(username);

        if(userEntity.isPresent()) {
          log.info("user update");
          Users modifyUser = userEntity.get();

          return modifyUser.getUsername();
        }
        Users users = Users.builder()
                .username(username)
                .password(extraWord)
                .providerId(dto.getId())
                .email(dto.getEmail())
                .role(ROLE.ROLE_USER)
                .build();

        userRepository.save(users);
        return username;
    }

}
