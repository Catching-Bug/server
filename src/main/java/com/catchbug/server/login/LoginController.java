package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
