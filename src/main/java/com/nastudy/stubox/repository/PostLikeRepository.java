package com.nastudy.stubox.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.nastudy.stubox.domain.entity.QPostLike.postLike;

@RequiredArgsConstructor
@Repository
public class PostLikeRepository {
    private final JPAQueryFactory queryFactory;

    public boolean receiveMyLike(Long memberId, Long postId) {
        Integer fetchFirst = queryFactory
                .selectOne()
                .from(postLike)
                .where(
                        postLike.member.id.eq(memberId)
                        , postLike.post.id.eq(postId)
                ).fetchFirst();
        return fetchFirst != null;
    }
}
