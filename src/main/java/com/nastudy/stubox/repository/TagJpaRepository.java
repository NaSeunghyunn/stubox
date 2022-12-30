package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameStartsWith(String name);

    Tag findByName(String name);

}
