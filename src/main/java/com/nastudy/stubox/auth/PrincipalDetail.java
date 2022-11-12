package com.nastudy.stubox.auth;

import com.nastudy.stubox.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements OAuth2User {
    private Long id;
    private String name;
    private String email;

    public PrincipalDetail(Member member, Map<String, Object> attributes) {
        this.id = member.getId();
        this.name = attributes.get("name").toString();
        this.email = attributes.get("email").toString();
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
        return name;
    }
}
