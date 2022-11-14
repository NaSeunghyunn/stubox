package com.nastudy.stubox.repository;

import com.nastudy.stubox.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m join fetch m.team where m.providerId = :providerId")
    Member findAuthentication(@Param("providerId") String providerId);
}
