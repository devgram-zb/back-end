package com.project.devgram.repository;

import com.project.devgram.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findByFollowing_UserSeq(Long userSeq);

}
