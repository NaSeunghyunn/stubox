package com.nastudy.stubox.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Card extends BaseEntity {
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

    @Builder
    public Card(Long id, String keyword, String concept, CardBox cardBox) {
        this.id = id;
        this.keyword = keyword;
        this.concept = concept;
        this.cardBox = cardBox;
    }

    public void modifyCard(String keyword, String concept) {
        this.keyword = keyword;
        this.concept = concept;
    }
}
