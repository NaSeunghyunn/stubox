package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.config.auth.attribute.Oauth2Attribute;
import com.nastudy.stubox.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements OAuth2User {
    private Long memberId;
    private String defaultName;
    private String defaultProfile;

    public PrincipalDetail(Member member, Oauth2Attribute attributes) {
        this.memberId = member.getId();
        this.defaultName = attributes.getNickname();
        this.defaultProfile = attributes.getProfile();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return defaultName;
    }
}
