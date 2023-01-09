package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Post;
import com.nastudy.stubox.domain.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostTagJpaRepository extends JpaRepository<PostTag, Long> {
    @Modifying
    @Query("delete from PostTag pt where pt.post = :post")
    int deleteByPost(@Param("post") Post post);
}
