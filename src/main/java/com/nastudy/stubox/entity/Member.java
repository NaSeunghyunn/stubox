package com.nastudy.stubox.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity{
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String email;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    private String provider;
    @Column(nullable = false, unique = true)
    private String providerId;

    @Builder
    public Member(Long id, String name, String email, Team team, String provider, String providerId) {
        Id = id;
        this.name = name;
        this.email = email;
        this.team = team;
        this.provider = provider;
        this.providerId = providerId;
    }
}
