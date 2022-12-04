package com.nastudy.stubox.repository;

import com.nastudy.stubox.controller.form.CardSaveForm;
import com.nastudy.stubox.domain.entity.Card;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.nastudy.stubox.domain.entity.QCard.card;
import static com.nastudy.stubox.domain.entity.QCardBox.cardBox;
import static com.nastudy.stubox.domain.entity.QMember.member;


@RequiredArgsConstructor
@Repository
public class CardRepository {
    private final JPAQueryFactory queryFactory;

    public boolean exists(CardSaveForm form) {
        Integer fetchFirst = queryFactory
                .selectOne()
                .from(card)
                .where(
                        card.keyword.eq(form.getKeyword())
                        , card.cardBox.id.eq(form.getBoxId())
                ).fetchFirst();
        return fetchFirst != null;
    }

    public Card findMyCard(Long cardId, Long memberId) {
        return queryFactory.select(card)
                .from(card)
                .join(card.cardBox, cardBox)
                .join(cardBox.member, member)
                .where(
                        card.id.eq(cardId)
                        , member.id.eq(memberId)
                ).fetchOne();
    }
}
