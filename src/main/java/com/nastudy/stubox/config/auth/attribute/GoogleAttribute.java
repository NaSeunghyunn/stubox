package com.nastudy.stubox.config.auth.attribute;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleAttribute implements Oauth2Attribute{
    private String nickname;
    private String profile;
    private String email;

    public GoogleAttribute(OAuth2User oAuth2User) {
        nickname = oAuth2User.getAttribute("name");
        profile = oAuth2User.getAttribute("picture");
        email = oAuth2User.getAttribute("email");
    }

    @Override
    public String getProfile() {
        return profile;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
