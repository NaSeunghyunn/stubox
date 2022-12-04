package com.nastudy.stubox.service;

import com.nastudy.stubox.config.auth.Auth2Service;
import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final Auth2Service auth2Service;

    public Long modName(Long memberId, String memberName) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디 입니다."));

        if (member.getName().equals(memberName)) {
            return memberId;
        }
        exsitsMemberName(memberName);
        member.changeNickName(memberName);
        auth2Service.modifyMember(member);
        return memberId;
    }

    private void exsitsMemberName(String memberName) {
        boolean existsByName = memberRepository.existsByName(memberName);
        if (existsByName) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }
    }
}
