package com.project.devgram.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {


    private String username;

    @Builder
    public UserDto( String username){
        this.username=username;
    }
}
