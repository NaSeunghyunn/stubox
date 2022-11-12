package com.nastudy.stubox.repository;

import com.nastudy.stubox.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByProviderId(String providerId);
}
