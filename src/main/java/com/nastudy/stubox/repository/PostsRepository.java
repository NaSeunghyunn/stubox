package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.Category;
import com.nastudy.stubox.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nastudy.stubox.domain.entity.QPosts.posts;
import static com.nastudy.stubox.domain.entity.QTeam.team;
import static com.nastudy.stubox.domain.entity.QTeamCategory.teamCategory;
import static java.util.stream.Collectors.*;


@RequiredArgsConstructor
@Repository
public class PostsRepository {
    private final JPAQueryFactory queryFactory;

    public List<PostsDto> findPosts(PostsCond cond) {
        List<PostsDto> result = queryFactory
                .select(new QPostsDto(
                        posts.id
                        , team.name
                        , posts.title
                ))
                .from(posts)
                .join(posts.team, team)
                .where(ltPostId(cond.getPostId())
                        , likeSearchContents(cond.getSearchContents())
                        , JPAExpressions
                                .selectOne()
                                .from(teamCategory)
                                .where(
                                        teamCategory.team.eq(team)
                                        , eqCategory(cond.getCategory())
                                ).exists()
                )
                .orderBy(posts.id.desc())
                .limit(6)
                .fetch();

        List<String> teamNames = result.stream().map(post -> post.getTeamName()).collect(toList());
        Map<String, Set<Category>> teamCategories = findTeamCategories(teamNames);
        result.forEach(post -> post.setCategories(
                teamCategories.get(post.getTeamName())
                .stream()
                .map(Category::getTitle)
                .collect(Collectors.toSet())));
        return result;
    }

    private Map<String, Set<Category>> findTeamCategories(List<String> teamNames) {
        List<TeamCategoryDto> teamCategories = queryFactory
                .select(new QTeamCategoryDto(
                        team.name
                        , teamCategory.category
                ))
                .from(teamCategory)
                .join(teamCategory.team, team)
                .where(
                        teamCategory.team.name.in(teamNames))
                .fetch();

        return teamCategories.stream()
                .collect(groupingBy(TeamCategoryDto::getTeamName, mapping(TeamCategoryDto::getCategory, toSet())));
    }

    private BooleanExpression ltPostId(Long postId) {
        return postId != null ? posts.id.lt(postId) : null;
    }

    private BooleanExpression likeSearchContents(String searchContents) {
        return StringUtils.hasText(searchContents) ?
                posts.title.like(searchContents + "%")
                .or(team.name.like(searchContents + "%")) : null;
    }

    private BooleanExpression eqCategory(Category category) {
        return category != null ? teamCategory.category.eq(category) : null;
    }
}
