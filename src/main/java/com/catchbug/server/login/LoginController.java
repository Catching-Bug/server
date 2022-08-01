package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.catchbug.server.oauth2.util.Oauth2RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@RequiredArgsConstructor
@RestController
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login/oauth")
    public ResponseEntity<?> oauth2Login(@RequestParam String code) throws JsonProcessingException {
        DtoOfLoginSuccess loginSuccess = loginService.login(code);
        return ResponseEntity.ok(loginSuccess);
    }
    //유저 정보 얻기
    @GetMapping("/kakao/auth")
    public ResponseEntity<?> auth(@RequestParam String code) throws JsonProcessingException {

        ResponseEntity<?> response = ResponseEntity.ok(loginService.login(code));

        return response;
    }






}
