package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestJpaRepository extends JpaRepository<Test, Long> {
}
