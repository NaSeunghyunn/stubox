package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.PostSaveForm;
import com.nastudy.stubox.domain.PostType;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Post;
import com.nastudy.stubox.domain.entity.PostTag;
import com.nastudy.stubox.domain.entity.Tag;
import com.nastudy.stubox.repository.PostJpaRepository;
import com.nastudy.stubox.repository.PostTagJpaRepository;
import com.nastudy.stubox.repository.TagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class KnowledgeService {
    private final PostJpaRepository postJpaRepository;
    private final TagJpaRepository tagJpaRepository;
    private final PostTagJpaRepository postTagJpaRepository;
    private final Auth2Service auth2Service;

    public Long save(PostSaveForm form, Long memberId){
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

    private void savePostTags(Post post, List<Long> tagIds){
        List<Tag> tags = tagJpaRepository.findAllById(tagIds);
        for(Tag tag : tags){
            PostTag postTag = PostTag.builder().post(post).tag(tag).build();
            postTagJpaRepository.save(postTag);
        }
    }
}
