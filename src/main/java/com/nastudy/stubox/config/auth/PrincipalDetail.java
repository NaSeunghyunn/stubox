package com.nastudy.stubox.config.auth;

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
    private Map<String, Object> attributes;

    public PrincipalDetail(Member member, Map<String, Object> attributes) {
        this.memberId = member.getId();
        this.defaultName = attributes.get("name").toString();
        this.defaultProfile = attributes.get("picture").toString();
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
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
