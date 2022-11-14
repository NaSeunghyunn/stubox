package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements OAuth2User {
    private Member member;

    public PrincipalDetail(Member member) {
        this.member = member;
    }

    public Long getId(){
        return member.getId();
    }

    public Long getTeamId(){
        return member.getTeam() == null ? null : member.getTeam().getId();
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
        return member.getName();
    }
}
