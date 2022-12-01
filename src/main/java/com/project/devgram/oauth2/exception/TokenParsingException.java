package com.project.devgram.oauth2.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenParsingException extends Throwable {

    private String description;

   public TokenParsingException(String description){
       this.description=description;
   }
}
