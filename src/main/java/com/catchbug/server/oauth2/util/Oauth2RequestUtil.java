package com.catchbug.server.oauth2.util;

import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.Map;

/**
 * <h1>Oauth2RequestUtil</h1>
 * <p>
 *     Oauth2 Request Object
 * </p>
 * <p>
 *     Oauth2 Request와 Response를 직접 받을 클래스
 * </p>
 *
 * @see com.catchbug.server.oauth2.util.Oauth2Util
 * @author younghoCha
 */
@Component
public class Oauth2RequestUtil {

    private ObjectMapper objectMapper;

    public Oauth2RequestUtil(){
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * Oauth2 Authorization Server 에 Access Token을 요청하는 메서드
     * @param request : Api에 맞게 만들어진 request
     * @return DtoOfOauthTokenResponse : 받은 AccessToken을 바인딩한 Dto 객체
     * @throws JsonProcessingException
     */
    public DtoOfOauthTokenResponse requestAuth(HttpEntity request) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        ).getBody();
        DtoOfOauthTokenResponse dtoOfOauthTokenResponse =
                objectMapper.readValue(responseBody, DtoOfOauthTokenResponse.class);
        return dtoOfOauthTokenResponse;
    }

    /**
     * Oauth2 Resource 서버에 AccessToken을 통해서 User Profile을 요청하는 메서드
     * @param accessToken : Oauth2 Authorization 서버로 부터 받은 Access Token
     * @return DtoOfUserProfile : Oauth2 Resource 서버로 부터 받은 User Profile
     * @throws JsonProcessingException
     */
    public DtoOfUserProfile requestProfile(String accessToken) throws JsonProcessingException {


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> request =
                new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        String responseBody = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class
        ).getBody().toString();


        DtoOfUserProfile dtoOfUserProfile = objectMapper.readValue(responseBody, DtoOfUserProfile.class);

        return dtoOfUserProfile;

    }

}
