package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m, t from Member m left join m.team t where m.providerId = :providerId")
    Member findAuthentication(@Param("providerId") String providerId);

    @Query("select m, t from Member m left join m.team t where m.id = :id")
    Member findMember(@Param("id") Long id);

    List<Member> findByTeam(Team team);

    boolean existsByName(String name);

    @Modifying
    @Query("update Member m set m.team = null, m.teamRole = com.nastudy.stubox.domain.TeamRole.NONE where m.team = :team")
    int withdrawTeam(@Param("team") Team team);
}
