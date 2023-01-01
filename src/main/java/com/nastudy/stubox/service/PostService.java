package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.PostsSaveForm;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Posts;
import com.nastudy.stubox.dto.PostsDetailDto;
import com.nastudy.stubox.dto.PostsCond;
import com.nastudy.stubox.dto.PostsResponse;
import com.nastudy.stubox.repository.PostsJpaRepository;
import com.nastudy.stubox.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostsJpaRepository postJpaRepository;
    private final PostsRepository postRepository;
    private final Auth2Service auth2Service;

    public Long save(PostsSaveForm form, Long memberId) {
        Member member = auth2Service.findMember(memberId);
        auth2Service.isMaster(member);
        Posts posts = Posts.builder().title(form.getTitle()).content(form.getContent()).team(member.getTeam()).build();
        postJpaRepository.save(posts);
        return member.getTeam().getId();
    }

    @Transactional(readOnly = true)
    public PostsResponse findPosts(PostsCond cond) {
        return new PostsResponse(postRepository.findPosts(cond));
    }

    @Transactional(readOnly = true)
    public PostsDetailDto findPostDetail(Long id){
        Posts posts = postJpaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("모집글이 존재하지 않습니다."));
        return new PostsDetailDto(posts);
    }
}
