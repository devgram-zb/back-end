package com.project.devgram.entity;

import com.project.devgram.type.ROLE;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User extends BaseEntity {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSeq;
	private String username;
	private String password;
	private String email;

	@Enumerated(EnumType.STRING)
	private ROLE role;

	private String providerId;
	private String annual;
	private String job;

	private int followCount;
	private int followerCount;

	@Builder.Default
	@OneToMany(mappedBy = "following",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Follow> followingList = new ArrayList<>() ;

	@Builder.Default
	@OneToMany(mappedBy = "follower",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Follow> followerList = new ArrayList<>() ;




}
