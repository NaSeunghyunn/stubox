package com.nastudy.stubox.repository;

import com.nastudy.stubox.dto.QTestDto;
import com.nastudy.stubox.dto.TestCond;
import com.nastudy.stubox.dto.TestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nastudy.stubox.domain.entity.QCard.card;
import static com.nastudy.stubox.domain.entity.QCardBox.cardBox;
import static com.nastudy.stubox.domain.entity.QCardTest.cardTest;

@RequiredArgsConstructor
@Repository
public class CardTestRepository {
    private final JPAQueryFactory queryFactory;

    public boolean exists(Long memberId, Long cardId){
        Integer fetchFirst = queryFactory
                .selectOne()
                .from(cardTest)
                .where(
                        cardTest.member.id.eq(memberId)
                        , cardTest.card.id.eq(cardId)
                )
                .fetchFirst();
        return fetchFirst != null;
    }

    public List<TestDto> selectTestData(TestCond cond) {
        return queryFactory
                .select(new QTestDto(cardTest.id, card.id, card.keyword, card.concept))
                .from(cardBox)
                .join(card).on(card.cardBox.eq(cardBox))
                .leftJoin(cardTest).on(cardTest.card.eq(card), cardTest.member.id.eq(cond.getMemberId()))
                .where(cardBox.id.eq(cond.getSelectBoxId())
                        , eqLevel(cond.getLevel()))
                .fetch();
    }

    private BooleanExpression eqLevel(int level) {
        return level == 1 ? cardTest.isNull().or(cardTest.level.eq(level)) : cardTest.level.eq(level);
    }
}
