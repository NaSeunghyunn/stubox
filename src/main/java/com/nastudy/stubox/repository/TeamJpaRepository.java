package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
}
