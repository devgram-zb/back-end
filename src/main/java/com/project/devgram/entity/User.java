package com.project.devgram.entity;

import com.project.devgram.type.ROLE;
import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

}
