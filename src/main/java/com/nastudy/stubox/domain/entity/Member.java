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
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    private String provider;
    @Column(nullable = false, unique = true)
    private String providerId;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "select_box_id")
    private CardBox cardBox;
    @Enumerated(EnumType.STRING)
    private TeamRole teamRole = TeamRole.NONE;

    @Builder
    public Member(Long id, String name, String email, Team team, String provider, String providerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.team = team;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void selectBox(CardBox cardBox) {
        this.cardBox = cardBox;
    }

    public void createTeam(Team team){
        this.team = team;
        this.teamRole = TeamRole.MASTER;
    }
}
