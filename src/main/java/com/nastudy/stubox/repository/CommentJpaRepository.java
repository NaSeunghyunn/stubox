package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    @Query("select c, w, r from Comment c inner join c.writer w left join c.receiver r where c.post.id = :postId")
    List<Comment> findByPostId(@Param("postId") Long postId);

    @Query("select c from Comment c where c.id = :commentId and c.writer.id = :memberId")
    Optional<Comment> findMyComment(@Param("commentId") Long commentId, @Param("memberId") Long memberId);
}
