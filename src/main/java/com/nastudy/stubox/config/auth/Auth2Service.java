package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.domain.entity.Member;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Auth2Service {
    private final MemberRepository memberRepository;

    public PrincipalDetail modifyMember(Member member) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        PrincipalDetail newPrincipalDetail = new PrincipalDetail(member, principalDetail.getAttributes());

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(newPrincipalDetail, newPrincipalDetail.getAuthorities(), member.getProviderId());
        context.setAuthentication(token);
        return newPrincipalDetail;
    }

    public PrincipalDetail refresh(PrincipalDetail principal) {
        Member member = memberRepository.findById(principal.getId()).orElseThrow(() -> new IllegalArgumentException("다시 로그인 해주세요."));
        if (refreshTeam(member, principal)) {
            return modifyMember(member);
        }
        return principal;
    }

    private boolean refreshTeam(Member member, PrincipalDetail principalDetail) {
        if (member.getTeam() == null) {
            return true;
        }
        if (!member.getTeam().getId().equals(principalDetail.getTeamId())) {
            return true;
        }
        if (member.getTeamRole() != principalDetail.getTeamRole()) {
            return true;
        }
        return false;
    }
}
