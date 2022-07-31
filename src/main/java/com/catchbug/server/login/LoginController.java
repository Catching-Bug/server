package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login/oauth/{provider}")
    public DtoOfLoginSuccess oauth2Login(@PathVariable String provider,  @RequestParam String code){

        return loginService.login("kakao", code);
    }

    @GetMapping("/kakao/auth")
    public ResponseEntity<String> auth(@RequestParam String code){
        log.info("여기 들어옴");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokebRequest =
                new HttpEntity<>(generateParam(code), headers);
        log.info("여기2");
        return requestAuth(kakaoTokebRequest);
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

    private ResponseEntity<String> requestAuth(HttpEntity request){
        RestTemplate restTemplate = new RestTemplate();
        log.info("여기3");
        return restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }


}
