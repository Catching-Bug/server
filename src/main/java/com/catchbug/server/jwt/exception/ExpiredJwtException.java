package com.catchbug.server.jwt.exception;


import org.springframework.security.core.AuthenticationException;
/**
 * <h1>ExpiredJwtException</h1>
 * <p>
 *     Exception than Expired JWT
 * </p>
 * <p>
 *     JWT 만료 예외
 * </p>
 *
 * @see com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler
 * @author younghoCha
 */
public class ExpiredJwtException extends AuthenticationException {
    public ExpiredJwtException(String msg){
        super(msg);
    }
}
