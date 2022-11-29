package com.project.devgram.oauth.entity;

import com.project.devgram.common.BaseEntity;
import com.project.devgram.oauth.Provider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM - Object Relation Mapping

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String email;
	private String role; //ROLE_USER, ROLE_ADMIN
	// OAuth를 위해 구성한 추가 필드 2개

	private String annual;
	private String job;

	private int followCount;
	private int followerCount;

	private String createdBy;
	private String updatedBy;


}
