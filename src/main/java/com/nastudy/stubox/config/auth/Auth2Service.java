package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.domain.TeamRole;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class Auth2Service {
    private final MemberRepository memberRepository;

    public Member findMember(Long memberId) {
        Member member = memberRepository.findMember(memberId);
        if (member == null) {
            throw new IllegalArgumentException("다시 로그인 해 주세요.");
        }
        return member;
    }

    public Member findMemberByName(String name) {
        Member member = memberRepository.findMember(name);
        if (member == null) {
            throw new IllegalArgumentException("다시 로그인 해 주세요.");
        }
        return member;
    }

    public Long getTeamId(Member member) {
        return member.getTeam() == null ? null : member.getTeam().getId();
    }

    public String getTeamName(Member member) {
        return member.getTeam() == null ? null : member.getTeam().getName();
    }

    public void isMaster(Member member) {
        hasTeam(member);
        if (member.getTeamRole() != TeamRole.MASTER) {
            throw new IllegalArgumentException("관리자가 아닙니다.");
        }
    }

    public void hasTeam(Member member) {
        if (member.getTeam() == null) {
            throw new IllegalArgumentException("팀이 없습니다.");
        }
    }
}
