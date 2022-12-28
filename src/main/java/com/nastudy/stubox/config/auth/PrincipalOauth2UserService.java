package com.nastudy.stubox.config.auth;

import com.nastudy.stubox.config.auth.attribute.KakaoAttribute;
import com.nastudy.stubox.config.auth.attribute.Oauth2Attribute;
import com.nastudy.stubox.domain.entity.Member;
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
        Oauth2Attribute oauth2Attribute = new KakaoAttribute(oAuth2User);

        Member member = memberRepository.findAuthentication(oAuth2User.getName());

        if (member == null) {
            member = Member.builder()
                    .name(oauth2Attribute.getNickname())
                    .email(oauth2Attribute.getEmail())
                    .profile(oauth2Attribute.getProfile())
                    .provider(userRequest.getClientRegistration().getRegistrationId())
                    .providerId(oAuth2User.getName())
                    .build();

            memberRepository.save(member);
        }
        return new PrincipalDetail(member, oauth2Attribute);
    }
}
