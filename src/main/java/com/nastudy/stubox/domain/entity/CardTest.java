package com.nastudy.stubox.domain.entity;

import com.nastudy.stubox.dto.TestResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CardTest extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ColumnDefault("1")
    private int level;

    @Builder
    public CardTest(Long id, Card card, Member member, int level) {
        this.id = id;
        this.card = card;
        this.member = member;
        this.level = level;
    }

    public void reLeveling(TestResult testResult) {
        if (testResult == TestResult.NG && this.level < 5) {
            this.level++;
        }
    }
}
