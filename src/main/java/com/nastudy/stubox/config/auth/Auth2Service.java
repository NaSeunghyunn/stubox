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

    public void modifyMember(Member member) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        PrincipalDetail newPrincipalDetail = new PrincipalDetail(member, principalDetail.getAttributes());

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(newPrincipalDetail, newPrincipalDetail.getAuthorities(), member.getProviderId());
        context.setAuthentication(token);
    }
}
