package com.nastudy.stubox.repository;

import com.nastudy.stubox.controller.form.CardSaveForm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.nastudy.stubox.domain.entity.QCard.card;


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
}
