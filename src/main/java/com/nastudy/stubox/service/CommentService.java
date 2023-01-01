package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.controller.form.CommentChildSaveForm;
import com.nastudy.stubox.controller.form.CommentSaveForm;
import com.nastudy.stubox.domain.entity.Comment;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Post;
import com.nastudy.stubox.dto.CommentDto;
import com.nastudy.stubox.repository.CommentJpaRepository;
import com.nastudy.stubox.repository.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentJpaRepository commentJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final Auth2Service auth2Service;

    public Long save(CommentSaveForm form, Long memberId) {
        Post post = postJpaRepository.findById(form.getPostId()).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Member member = auth2Service.findMember(memberId);
        Comment comment = Comment.builder()
                .content(form.getContent())
                .post(post)
                .writer(member)
                .build();
        commentJpaRepository.save(comment);
        return comment.getId();
    }

    public Long saveChild(CommentChildSaveForm form, Long memberId) {
        Comment parent = commentJpaRepository.findById(form.getId()).orElseThrow(() -> new IllegalArgumentException("삭제된 댓글입니다."));
        Member member = auth2Service.findMember(memberId);
        Member receiver = auth2Service.findMemberByName(form.getReceiverName());
        Comment comment = Comment.builder()
                .content(form.getContent())
                .writer(member)
                .receiver(receiver)
                .parent(parent)
                .build();
        commentJpaRepository.save(comment);
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAll(Long postId) {
        List<Comment> comments = commentJpaRepository.findByPostId(postId);
        return comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public Long delete(Long commentId, Long memberId) {
        Comment comment = commentJpaRepository.findMyComment(commentId, memberId).orElseThrow(() -> new IllegalArgumentException("삭제 할 수 없습니다."));
        commentJpaRepository.delete(comment);
        return commentId;
    }
}
