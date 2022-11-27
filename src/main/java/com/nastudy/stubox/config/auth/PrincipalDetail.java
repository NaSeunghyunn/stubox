package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements OAuth2User {
    private Member member;
    private Map<String, Object> attributes;

    public PrincipalDetail(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    public Long getId() {
        return member.getId();
    }

    public Long getTeamId() {
        return member.getTeam() == null ? null : member.getTeam().getId();
    }

    public TeamRole getTeamRole(){
        return member.getTeamRole();
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
        return member.getName();
    }

    public void checkMaster() {
        if (member.getTeamRole() != TeamRole.MASTER) throw new IllegalStateException("관리자가 아닙니다.");
    }

    public void hasTeam() {
        if (member.getTeam() == null) throw new IllegalStateException("팀이 없습니다.");
    }
}
