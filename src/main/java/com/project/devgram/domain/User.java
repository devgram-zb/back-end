package com.project.devgram.domain;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;

    private String refreshToken;





}
