package com.project.devgram.service;

import com.project.devgram.dto.FollowDto;
import com.project.devgram.entity.Follow;
import com.project.devgram.entity.User;
import com.project.devgram.oauth2.response.UserResponse;
import com.project.devgram.repository.FollowRepository;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;


    @Transactional
    public UserResponse followAdd(FollowDto dto) {
        log.info("dtos : {} ", dto);

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        Optional<User> optionalFollowing = userRepository.findByUsername(dto.getFollowingUsername());
        List<Follow> followList = followRepository.findByFollowing_UserSeq(dto.getFollowingUserSeq());


        if(optionalUser.isPresent() && optionalFollowing.isPresent() && followList.size() == 0) {
            // 내가 팔로우 신청을 했을때
           User user = optionalUser.get();
           user.setFollowCount(user.getFollowCount()+1);

           User followingUser = optionalFollowing.get();
           followingUser.setFollowerCount(user.getFollowerCount()+1);

           User follower = User.builder()
                   .userSeq(user.getUserSeq())
                   .followerList(new ArrayList<>())
                   .followingList(new ArrayList<>())
                   .build();

           User following = User.builder()
                   .userSeq(followingUser.getUserSeq())
                   .followingList(new ArrayList<>())
                   .followerList(new ArrayList<>())
                   .build();

           Follow followers = new Follow();
           followers.setFollower(follower);
           followers.setFollowing(following);


            user.getFollowingList().add(followers);

            userRepository.save(user);


           return new UserResponse(String.valueOf(ResponseEnum.success), "following add success");
        }

        return new UserResponse(String.valueOf(ResponseEnum.fail),"이미 등록된 유저입니다.");
        }


}
