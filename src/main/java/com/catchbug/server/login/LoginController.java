package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login/oauth")
    public DtoOfLoginSuccess oauth2Login(@RequestParam String code){

        return loginService.login(code);
    }
}
