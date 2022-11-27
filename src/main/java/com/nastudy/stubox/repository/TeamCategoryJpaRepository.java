package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Team;
import com.nastudy.stubox.domain.entity.TeamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamCategoryJpaRepository extends JpaRepository<TeamCategory, Long> {

    int deleteByTeam(Team team);
}
