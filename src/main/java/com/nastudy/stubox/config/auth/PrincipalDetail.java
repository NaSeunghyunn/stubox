package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Team;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetail implements OAuth2User {
    private Long memberId;
    private String memberName;
    private Long teamId;
    private String teamName;
    private TeamRole teamRole;
    private String profile;
    private Map<String, Object> attributes;

    public PrincipalDetail(Member member, Map<String, Object> attributes) {
        this.memberId = member.getId();
        this.memberName = member.getName();
        Team team = member.getTeam();
        this.teamId = team == null ? null : team.getId();
        this.teamName = team == null ? null : team.getName();
        this.teamRole = member.getTeamRole();
        this.profile = member.getProfile();
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
        return memberName;
    }

    public void checkMaster() {
        if (teamRole != TeamRole.MASTER) throw new IllegalStateException("관리자가 아닙니다.");
    }

    public void hasTeam() {
        if (teamId == null) throw new IllegalStateException("팀이 없습니다.");
    }
}
