package com.nastudy.stubox.repository;

import com.nastudy.stubox.dto.PostTagDto;
import com.nastudy.stubox.dto.QPostTagDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nastudy.stubox.domain.entity.QPostTag.postTag;
import static com.nastudy.stubox.domain.entity.QTag.tag;

@RequiredArgsConstructor
@Repository
public class PostTagRepository {
    private final JPAQueryFactory queryFactory;

    public List<PostTagDto> findPostTags(Long postId) {
        return queryFactory
                .select(new QPostTagDto(tag.name))
                .from(postTag)
                .join(postTag.tag, tag)
                .where(postTag.post.id.eq(postId))
                .fetch();
    }
}
