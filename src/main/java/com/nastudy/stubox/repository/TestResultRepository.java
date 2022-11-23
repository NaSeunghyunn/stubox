package com.nastudy.stubox.repository;

import com.nastudy.stubox.dto.QTestResultDto;
import com.nastudy.stubox.dto.TestResult;
import com.nastudy.stubox.dto.TestResultCond;
import com.nastudy.stubox.dto.TestResultDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nastudy.stubox.domain.entity.QCard.card;
import static com.nastudy.stubox.domain.entity.QCardBox.cardBox;
import static com.nastudy.stubox.domain.entity.QCardTest.cardTest;
import static com.nastudy.stubox.domain.entity.QTest.test;

@RequiredArgsConstructor
@Repository
public class TestResultRepository {
    private final JPAQueryFactory queryFactory;

    public List<TestResultDto> findTestResults(TestResultCond cond) {
        return queryFactory.select(
                        new QTestResultDto(
                                card.keyword
                                , test.level
                                , test.testResult
                                , test.testDate))
                .from(test)
                .join(test.cardTest, cardTest).on(cardTest.member.id.eq(cond.getMemberId()))
                .join(cardTest.card, card)
                .join(card.cardBox, cardBox).on(cardBox.id.eq(cond.getBoxId()))
                .where(
                        test.testDate.after(cond.getFrom())
                        , test.testDate.before(cond.getTo())
                        , testResultEq(cond.getTestResult())
                ).orderBy(test.testDate.asc())
                .fetch();
    }

    private BooleanExpression testResultEq(TestResult testResult) {
        return testResult != null ? test.testResult.eq(testResult) : null;
    }
}
