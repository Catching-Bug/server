package com.catchbug.server.jwt.exception;


import org.springframework.security.core.AuthenticationException;

public class ExpiredJwtException extends AuthenticationException {
    public ExpiredJwtException(String msg){
        super(msg);
    }
}
