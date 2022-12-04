package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.domain.TeamRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String profile;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    private String provider;
    @Column(nullable = false, unique = true)
    private String providerId;
    @Enumerated(EnumType.STRING)
    private TeamRole teamRole = TeamRole.NONE;

    @Builder
    public Member(Long id, String name, String email, String profile, Team team, String provider, String providerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.team = team;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void joinTeam(Team team, TeamRole teamRole) {
        this.team = team;
        this.teamRole = teamRole;
    }

    public void removeTeam() {
        this.team = null;
        this.teamRole = TeamRole.NONE;
    }

    public void auth(TeamRole teamRole) {
        this.teamRole = teamRole;
    }

    public void changeNickName(String name) {
        this.name = name;
    }
}
