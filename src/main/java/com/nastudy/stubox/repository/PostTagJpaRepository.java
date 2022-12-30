package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagJpaRepository extends JpaRepository<PostTag, Long> {
}
