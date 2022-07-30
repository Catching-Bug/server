package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;

import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import com.catchbug.server.oauth2.InMemoryProviderRepository;
import com.catchbug.server.oauth2.OauthAttributes;
import com.catchbug.server.oauth2.OauthProvider;
import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final InMemoryProviderRepository inMemoryProviderRepository;

    public DtoOfLoginSuccess login(String providerName, String authorizationCode){

        OauthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);
        DtoOfOauthTokenResponse tokenResponse = getToken(authorizationCode, provider);
        // 유저 정보 가져오기
        DtoOfUserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);
        return DtoOfLoginSuccess.builder()
                .nickname(userProfile.getName())
                .accessToken("access token")
                .refreshToken("refresh tokeb")
                .gender(Gender.MALE)
                .build();

    }

    private DtoOfUserProfile getUserProfile(String providerName, DtoOfOauthTokenResponse tokenResponse, OauthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        // TODO 유저 정보(map)를 통해 UserProfile 만들기
        return OauthAttributes.extract(providerName, userAttributes);
    }


    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OauthProvider provider, DtoOfOauthTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri("https://kauth.kakao.com/oauth/token")
                .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }


    private DtoOfOauthTokenResponse getToken(String code, OauthProvider provider) {
        return WebClient.create()
                .post()
                .uri("https://kauth.kakao.com/oauth/token")
                .headers(header -> {
                    header.setBasicAuth("1e9f53ef5d1b41846fe3d2e9e47a0e51", "rcgeTZx1l9QBCMGPNY6TwUnjbU8dS5FV");
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(tokenRequest(code, provider))
                .retrieve()
                .bodyToMono(DtoOfOauthTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OauthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", "http://localhost:8080/redirect/oauth");
        return formData;
    }
}
