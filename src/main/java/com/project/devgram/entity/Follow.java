package com.project.devgram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    private User following;

    public void setFollower(User follower){
        this.follower=follower;
        follower.getFollowingList().add(this);


    }

    public void setFollowing(User following){
        this.following=following;
        following.getFollowingList().add(this);

    }



}
