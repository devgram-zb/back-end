package com.project.devgram.oauth2.provider;

import java.util.Map;

public class GithubUser implements OAuthUserInfo {

    private Map<String,Object> attribute;

    public GithubUser(Map<String,Object> attribute){
        this.attribute=attribute;
    }

    @Override
    public String getProviderId() {
        return (String) attribute.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getLoginId() {
        return  (String) attribute.get("login");
    }
}
