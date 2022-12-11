package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.CardBox;
import com.nastudy.stubox.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nastudy.stubox.domain.entity.QCard.card;
import static com.nastudy.stubox.domain.entity.QCardBox.cardBox;
import static com.nastudy.stubox.domain.entity.QCardTest.cardTest;
import static com.nastudy.stubox.domain.entity.QMember.member;
import static com.nastudy.stubox.domain.entity.QTeam.team;
import static com.nastudy.stubox.domain.entity.QTest.test;


@RequiredArgsConstructor
@Repository
public class CardBoxRepository {
    private final JPAQueryFactory queryFactory;

    public List<BoxDto> searchByMember(Long memberId) {
        return queryFactory.select(new QBoxDto(cardBox.id, cardBox.name, member.profile, member.name))
                .from(member)
                .join(cardBox).on(cardBox.member.eq(member))
                .where(
                        member.id.eq(memberId))
                .fetch();
    }

    public List<BoxDto> searchByTeam(Long teamId) {
        return queryFactory.select(new QBoxDto(cardBox.id, cardBox.name, member.profile, member.name))
                .from(team)
                .join(member).on(member.team.eq(team), member.teamRole.ne(TeamRole.UNAPPROVED))
                .join(cardBox).on(cardBox.member.eq(member))
                .where(
                        team.id.eq(teamId))
                .fetch();
    }

    public boolean existsByName(Long memberId, String name) {
        Integer fetchFirst = queryFactory
                .selectOne()
                .from(cardBox)
                .where(
                        cardBox.member.id.eq(memberId)
                        , cardBox.name.eq(name))
                .fetchFirst();
        return fetchFirst != null;
    }

    public boolean isGroupBox(Long boxId, Long memberId, Long teamId) {
        Integer fetchFirst = queryFactory
                .selectOne()
                .from(cardBox)
                .join(cardBox.member, member)
                .leftJoin(member.team, team)
                .where(
                        cardBox.id.eq(boxId)
                        , isMyGroup(memberId, teamId)
                )
                .fetchFirst();
        return fetchFirst != null;
    }

    public CardBox myCardBox(Long boxId, Long memberId) {
        return queryFactory
                .select(cardBox)
                .from(cardBox)
                .where(
                        cardBox.id.eq(boxId)
                        , cardBox.member.id.eq(memberId)
                )
                .fetchOne();
    }

    public BoxDeleteDto selectBoxDeleteDto(Long boxId, Long memberId) {
        List<BoxIdDto> boxIds = queryFactory
                .select(new QBoxIdDto(
                        Expressions.asNumber(boxId)
                        , card.id
                        , cardTest.id
                        , test.id))
                .from(cardBox)
                .leftJoin(card).on(card.cardBox.id.eq(cardBox.id))
                .leftJoin(cardTest).on(cardTest.card.id.eq(card.id))
                .leftJoin(test).on(test.cardTest.id.eq(cardTest.id))
                .where(
                        cardBox.id.eq(boxId)
                        , cardBox.member.id.eq(memberId))
                .fetch();

        return getBoxDeleteDto(boxIds);
    }

    private BoxDeleteDto getBoxDeleteDto(List<BoxIdDto> boxIds) {
        if(boxIds.isEmpty()){
            return null;
        }

        BoxDeleteDto boxDeleteDto = new BoxDeleteDto();
        boxIds.forEach(boxDeleteDto::put);
        return boxDeleteDto;
    }

    private BooleanExpression boxIdEq(Long boxId) {
        return boxId != null && boxId > 0 ? cardBox.id.eq(boxId) : null;
    }

    private BooleanExpression isMyGroup(Long memberId, Long teamId) {
        if (teamId == null) {
            return memberIdEq(memberId);
        }
        return teamIdEq(teamId);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? member.id.eq(memberId) : null;
    }

    private BooleanExpression teamIdEq(Long teamId) {
        return teamId != null ? team.id.eq(teamId) : null;
    }
}
