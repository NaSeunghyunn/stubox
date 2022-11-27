package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Posts;
import com.nastudy.stubox.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostJpaRepository extends JpaRepository<Posts, Long> {
    @Modifying
    @Query("delete from Posts p where p.team = :team")
    int deleteByTeam(@Param("team") Team team);
}
