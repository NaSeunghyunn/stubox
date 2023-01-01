package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostJpaRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.writer where p.id = :id")
    Post findPost(@Param("id") Long id);
}
