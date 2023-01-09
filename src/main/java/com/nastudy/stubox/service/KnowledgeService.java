package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.PostEditForm;
import com.nastudy.stubox.controller.form.PostForm;
import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.domain.PostSearchType;
import com.nastudy.stubox.domain.PostType;
import com.nastudy.stubox.domain.entity.*;
import com.nastudy.stubox.dto.PostDetailDto;
import com.nastudy.stubox.dto.PostDto;
import com.nastudy.stubox.dto.PostEditDto;
import com.nastudy.stubox.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class KnowledgeService {
    private final PostRepository postRepository;
    private final PostJpaRepository postJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final PostTagJpaRepository postTagJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final PostLikeJpaRepository postLikeJpaRepository;
    private final Auth2Service auth2Service;

    public Page<PostDto> findPosts(PostForm form) {
        if (form.getSearchType() == PostSearchType.TAG) {
            return postRepository.selectByTag(form);
        }
        return postRepository.selectByTitle(form);
    }

    public Page<PostDto> countPosts(PostForm form) {
        if (form.getSearchType() == PostSearchType.TAG) {
            return postRepository.totalCountByTag(form);
        }
        return postRepository.totalCountByTitle(form);
    }

    public PostDetailDto findPost(Long postId) {
        Post post = postJpaRepository.findPost(postId);
        if (post == null) {
            throw new IllegalArgumentException("지식글이 존재하지 않습니다.");
        }
        post.viewCountUp();
        return new PostDetailDto(post);
    }

    public Long save(PostSaveForm form, Long memberId) {
        Member member = auth2Service.findMember(memberId);

        Post post = Post.builder()
                .postType(PostType.KNOWLEDGE)
                .title(form.getTitle())
                .content(form.getContent())
                .previewURL(form.getPreviewURL())
                .writer(member).build();

        postJpaRepository.save(post);
        savePostTags(post, form.getTagIds());
        return post.getId();
    }

    public PostEditDto findEditPost(Long postId, Long memberId) {
        return postRepository.findEditPost(postId, memberId);
    }

    public Long edit(PostEditForm form, Long memberId) {
        Post post = postJpaRepository.findMyPost(form.getId(), memberId).orElseThrow(() -> new IllegalArgumentException("수정 권한이 없습니다."));
        post.edit(form.getTitle(), form.getContent(), form.getPreviewURL());
        postTagJpaRepository.deleteByPost(post);
        savePostTags(post, form.getTagIds());
        return post.getId();
    }

    public Long delete(Long postId, Long memberId) {
        Post post = postJpaRepository.findMyPost(postId, memberId).orElseThrow(() -> new IllegalArgumentException("수정 권한이 없습니다."));
        commentJpaRepository.deleteByPost(post);
        postTagJpaRepository.deleteByPost(post);
        postLikeJpaRepository.deleteByPost(post);
        postJpaRepository.delete(post);
        return postId;
    }

    private void savePostTags(Post post, List<Long> tagIds) {
        List<Tag> tags = tagJpaRepository.findAllById(tagIds);
        for (Tag tag : tags) {
            PostTag postTag = PostTag.builder().post(post).tag(tag).build();
            postTagJpaRepository.save(postTag);
        }
    }
}
