package com.catchbug.server.jwt.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DtoOfJwtAuthentication extends AbstractAuthenticationToken {
    private String token;

    public DtoOfJwtAuthentication(String token) {
        super(null); // 인증 전 객체이므로 권한 정보가 없음
        this.setAuthenticated(false);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}
