package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m, t from Member m left join m.team t where m.providerId = :providerId")
    Member findAuthentication(@Param("providerId") String providerId);

    @Query("select cb.id from Member m join m.cardBox cb where m.id = :id")
    Long findSelectedBoxId(@Param("id") Long id);
}
