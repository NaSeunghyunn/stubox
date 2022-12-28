package com.nastudy.stubox.config.auth.attribute;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Oauth2AttributeUtil {

    public Oauth2Attribute createAttribute(String registrationId, OAuth2User oAuth2User){
        if("kakao".equals(registrationId)){
            return new KakaoAttribute(oAuth2User);
        }
        return new GoogleAttribute(oAuth2User);
    }
}
