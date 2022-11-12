package com.nastudy.stubox.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String email;
    private String provider;
    @Column(nullable = false, unique = true)
    private String providerId;

    @Builder
    public Member(Long id, String name, String email, String provider, String providerId) {
        Id = id;
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }
}
