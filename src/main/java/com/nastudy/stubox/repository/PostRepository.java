package com.nastudy.stubox.repository;

import com.nastudy.stubox.controller.form.PostForm;
import com.nastudy.stubox.domain.PostSortType;
import com.nastudy.stubox.dto.PostDto;
import com.nastudy.stubox.dto.PostTagDto;
import com.nastudy.stubox.dto.QPostDto;
import com.nastudy.stubox.dto.QPostTagDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nastudy.stubox.domain.entity.QMember.member;
import static com.nastudy.stubox.domain.entity.QPost.post;
import static com.nastudy.stubox.domain.entity.QPostTag.postTag;
import static com.nastudy.stubox.domain.entity.QTag.tag;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final JPAQueryFactory queryFactory;

    public Page<PostDto> selectByTitle(PostForm form) {
        List<Long> ids = queryFactory.select(post.id)
                .from(post)
                .where(
                        searchTitle(form.getSearch())
                )
                .orderBy(postSort(form.getSort()))
                .limit(form.getSize())
                .offset((form.getPage()) * form.getSize())
                .fetch();

        List<PostDto> content = posts(form, ids);
        Map<Long, List<String>> tags = tags(ids);
        content.forEach(post -> post.setTags(tags.get(post.getId())));

        int totalCount = form.getTotalCount() == 0 ? form.getSize() * 3 : form.getTotalCount();
        return new PageImpl<>(content, PageRequest.of(form.getPage(), form.getSize()), totalCount);
    }

    public Page<PostDto> selectByTag(PostForm form) {
        List<Long> ids = queryFactory
                .select(post.id)
                .from(tag)
                .innerJoin(postTag).on(postTag.tag.eq(tag))
                .innerJoin(postTag.post, post)
                .where(
                        searchTag(form.getSearch())
                )
                .orderBy(postSort(form.getSort()))
                .limit(form.getSize())
                .offset((form.getPage()) * form.getSize())
                .fetch();

        List<PostDto> content = posts(form, ids);
        Map<Long, List<String>> tags = tags(ids);
        content.forEach(post -> post.setTags(tags.get(post.getId())));

        return new PageImpl<>(content, PageRequest.of(form.getPage(), form.getSize()), form.getSize() * 3);
    }

    public Page<PostDto> totalCountByTitle(PostForm form) {
        Long totalCount = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        searchTitle(form.getSearch())
                ).fetchOne();
        return new PageImpl<>(new ArrayList<>(), PageRequest.of(form.getPage(), form.getSize()), totalCount);
    }

    public Page<PostDto> totalCountByTag(PostForm form) {
        Long totalCount = queryFactory
                .select(post.count())
                .from(tag)
                .innerJoin(postTag).on(postTag.tag.eq(tag))
                .innerJoin(postTag.post, post)
                .where(
                        searchTag(form.getSearch())
                ).fetchOne();
        return new PageImpl<>(new ArrayList<>(), PageRequest.of(form.getPage(), form.getSize()), totalCount);
    }

    private List<PostDto> posts(PostForm form, List<Long> ids) {
        return queryFactory.select(new QPostDto(
                        post.id
                        , post.title
                        , post.previewURL
                        , member.name
                        , member.profile
                        , post.updateDate
                        , post.viewCount
                        , post.commentCount
                        , post.likeCount
                ))
                .from(post)
                .join(post.writer, member)
                .where(
                        post.id.in(ids)
                )
                .orderBy(postSort(form.getSort()))
                .fetch();
    }

    private Map<Long, List<String>> tags(List<Long> ids) {
        List<PostTagDto> postTags = queryFactory.select(new QPostTagDto(postTag.post.id, tag.name)).from(postTag).join(postTag.tag, tag).where(postTag.post.id.in(ids)).fetch();
        return postTags.stream().collect(groupingBy(PostTagDto::getPostId, mapping(PostTagDto::getName, toList())));
    }

    private BooleanExpression searchTitle(String search) {
        return StringUtils.hasText(search) ? post.title.contains(search) : null;
    }

    private BooleanExpression searchTag(String search) {
        return StringUtils.hasText(search) ? tag.name.startsWith(search) : null;
    }

    private OrderSpecifier<?>[] postSort(PostSortType sort) {
        if (sort == null) {
            return new OrderSpecifier<?>[]{new OrderSpecifier<>(Order.DESC, post.id)};
        }

        switch (sort) {
            case RECOMMEND -> {
                return new OrderSpecifier<?>[]{new OrderSpecifier<>(Order.DESC, post.likeCount), new OrderSpecifier<>(Order.DESC, post.id)};
            }
            case VIEW -> {
                return new OrderSpecifier<?>[]{new OrderSpecifier<>(Order.DESC, post.viewCount), new OrderSpecifier<>(Order.DESC, post.id)};
            }
            case COMMENT -> {
                return new OrderSpecifier<?>[]{new OrderSpecifier<>(Order.DESC, post.commentCount), new OrderSpecifier<>(Order.DESC, post.id)};
            }
            default -> {
                return new OrderSpecifier<?>[]{new OrderSpecifier<>(Order.DESC, post.id)};
            }
        }
    }
}
