package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.dto.BoxDto;
import com.nastudy.stubox.dto.QBoxDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nastudy.stubox.domain.entity.QCardBox.cardBox;
import static com.nastudy.stubox.domain.entity.QMember.member;
import static com.nastudy.stubox.domain.entity.QTeam.team;


@RequiredArgsConstructor
@Repository
public class CardBoxRepository {
    private final JPAQueryFactory queryFactory;

    public List<BoxDto> searchByMember(Long memberId) {
        return queryFactory.select(new QBoxDto(cardBox.id, cardBox.name))
                .from(member)
                .join(cardBox).on(cardBox.member.eq(member))
                .where(
                        member.id.eq(memberId))
                .fetch();
    }

    public List<BoxDto> searchByTeam(Long teamId) {
        return queryFactory.select(new QBoxDto(cardBox.id, cardBox.name))
                .from(team)
                .join(member).on(member.team.eq(team), member.teamRole.ne(TeamRole.UNAPPROVED))
                .join(cardBox).on(cardBox.member.eq(member))
                .where(
                        team.id.eq(teamId))
                .fetch();
    }

    private BooleanExpression boxIdEq(Long boxId) {
        return boxId != null && boxId > 0 ? cardBox.id.eq(boxId) : null;
    }
}
