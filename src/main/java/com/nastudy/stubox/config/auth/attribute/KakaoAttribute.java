package com.nastudy.stubox.config.auth.attribute;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoAttribute implements Oauth2Attribute{
    private String nickname;
    private String profile;
    private String email;

    public KakaoAttribute(OAuth2User oAuth2User) {
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
        this.nickname = (String) kakaoProfile.get("nickname");
        this.profile = (String) kakaoProfile.get("profile_image_url");
        this.email = (String) kakaoAccount.get("email");
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
