package com.nastudy.stubox.service;

import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.domain.entity.Posts;
import com.nastudy.stubox.domain.entity.Team;
import com.nastudy.stubox.dto.PostsCond;
import com.nastudy.stubox.dto.PostsResponse;
import com.nastudy.stubox.repository.PostJpaRepository;
import com.nastudy.stubox.repository.PostRepository;
import com.nastudy.stubox.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final PostRepository postRepository;
    private final TeamJpaRepository teamJpaRepository;

    public Long save(PostSaveForm form, Long teamId) {
        Team team = teamJpaRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("팀이 없습니다."));
        Posts posts = Posts.builder().title(form.getTitle()).content(form.getContent()).team(team).build();
        postJpaRepository.save(posts);
        return teamId;
    }

    @Transactional(readOnly = true)
    public PostsResponse findPosts(PostsCond cond) {
        return new PostsResponse(postRepository.findPosts(cond));
    }
}
