package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class TeamCategory {

    @GeneratedValue
    @Id
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = LAZY)
    private Team team;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public TeamCategory(Long id, Team team, Category category) {
        this.id = id;
        this.team = team;
        this.category = category;
    }
}
