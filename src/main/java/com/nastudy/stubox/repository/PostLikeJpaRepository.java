package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, Long> {
    @Query("select pl from PostLike pl join fetch pl.member join fetch pl.post where pl.post.id = :postId")
    List<PostLike> findPostLikes(@Param("postId") Long postId);

    @Query("select pl from PostLike pl where pl.member.id = :memberId and pl.post.id = :postId")
    PostLike findMyPostLike(@Param("memberId") Long memberId, @Param("postId") Long postId);
}
