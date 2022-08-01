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

@Component
@Slf4j
public class Oauth2RequestUtil {

    private ObjectMapper objectMapper;

    public Oauth2RequestUtil(){
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
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
