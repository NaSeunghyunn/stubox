package com.nastudy.stubox.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Card extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String keyword;
    @Column(nullable = false)
    private String concept;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "box_id")
    private CardBox cardBox;
}
