package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
