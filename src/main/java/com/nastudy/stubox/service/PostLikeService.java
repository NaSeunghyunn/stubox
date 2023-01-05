package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Post;
import com.nastudy.stubox.domain.entity.PostLike;
import com.nastudy.stubox.dto.PostLikeDto;
import com.nastudy.stubox.repository.PostJpaRepository;
import com.nastudy.stubox.repository.PostLikeJpaRepository;
import com.nastudy.stubox.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeService {
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostJpaRepository postJpaRepository;
    private final Auth2Service auth2Service;

    @Transactional(readOnly = true)
    public PostLikeDto findPostLike(Long postId, Long memberId) {
        int likeCount = postLikeJpaRepository.findPostLikes(postId).size();
        boolean receiveMyLike = postLikeRepository.receiveMyLike(memberId, postId);
        return new PostLikeDto(likeCount, receiveMyLike);
    }

    public Long save(Long postId, Long memberId) {
        boolean receiveMyLike = postLikeRepository.receiveMyLike(memberId, postId);
        if (receiveMyLike) {
            throw new IllegalArgumentException("이미 좋아요를 누르셨습니다.");
        }

        Post post = postJpaRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        post.likeCountUp();
        Member member = auth2Service.findMember(memberId);
        PostLike postLike = PostLike.builder().post(post).member(member).build();
        postLikeJpaRepository.save(postLike);
        return postLike.getId();
    }

    public Long delete(Long postId, Long memberId) {
        PostLike myPostLike = postLikeJpaRepository.findMyPostLike(memberId, postId);
        if (myPostLike == null) {
            throw new IllegalArgumentException("아직 좋아요를 누르지 않았습니다.");
        }
        postLikeJpaRepository.delete(myPostLike);
        return myPostLike.getId();
    }
}
