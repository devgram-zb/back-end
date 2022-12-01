package com.project.devgram.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class passUtil {
    public static boolean equals(String plaintext, String hashed){

        if(plaintext ==null ||plaintext.length()<1){
            return false;

        }
        return BCrypt.checkpw(plaintext,hashed);
    }
    public static String encPassword (String plaintext){
        if(plaintext ==null ||plaintext.length()<1){
            return "";
        }
        return BCrypt.hashpw(plaintext,BCrypt.gensalt());
    }
}
