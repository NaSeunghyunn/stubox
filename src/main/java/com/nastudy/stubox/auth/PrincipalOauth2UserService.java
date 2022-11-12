package com.nastudy.stubox.auth;

import com.nastudy.stubox.entity.Member;
import com.nastudy.stubox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Member member = memberRepository.findByProviderId(oAuth2User.getName());

        if (member == null) {
            member = Member.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(oAuth2User.getAttribute("email"))
                    .provider(userRequest.getClientRegistration().getRegistrationId())
                    .providerId(oAuth2User.getName())
                    .build();

            memberRepository.save(member);
        }
        return new PrincipalDetail(member, oAuth2User.getAttributes());
    }
}
