package com.project.devgram.oauth2.provider;

public interface OAuthUserInfo {
    String getProviderId();
    String getEmail();
    String getLoginId();
}
