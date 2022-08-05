package com.catchbug.server.oauth2.util;

import com.catchbug.server.member.Gender;
import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@RequiredArgsConstructor
@Component
public class Oauth2Util {

    @Autowired
    private final Oauth2RequestUtil oauth2RequestUtil;


    public DtoOfOauthTokenResponse getToken(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<?> kakaoTokenRequest =
                new HttpEntity<>(generateParam(code), headers);

        DtoOfOauthTokenResponse dtoOfOauthTokenResponse = oauth2RequestUtil.requestAuth(kakaoTokenRequest);

        return dtoOfOauthTokenResponse;
    }


    private MultiValueMap<String, String> generateParam(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", "1e9f53ef5d1b41846fe3d2e9e47a0e51");
        params.add("redirect_uri", "http://localhost:8080/redirect/oauth");
        params.add("code", code);
        params.add("client_secret", "rcgeTZx1l9QBCMGPNY6TwUnjbU8dS5FV");

        return params;
    }

    public DtoOfUserProfile getUserProfile(String accessToken) throws JsonProcessingException {

        DtoOfUserProfile dtoOfUserProfile = oauth2RequestUtil.requestProfile(accessToken);

        if(dtoOfUserProfile.getKakaoAccount().getGender() == null){
            dtoOfUserProfile.getKakaoAccount().updateGender(Gender.NONE);
        }
        return dtoOfUserProfile;
    }

}
