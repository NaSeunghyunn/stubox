package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {

    @Query("select count(1) from Team t join Member m on m.team = t and m.teamRole = com.nastudy.stubox.domain.TeamRole.MASTER where t.id = :teamId")
    int countMaster(@Param("teamId") Long teamId);

    boolean existsByName(String name);

    Optional<Team> findByName(String name);
}
