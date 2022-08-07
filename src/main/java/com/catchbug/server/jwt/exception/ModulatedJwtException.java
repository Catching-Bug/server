package com.catchbug.server.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <h1>ModulatedJwtException</h1>
 * <p>
 *     Custom Exception for Malformed, Signature, Null Exception
 * </p>
 * <p>
 *    JWT 인증 도중 Malformed, Signature, Null Exception 시 발생하는 커스텀 Exception
 * </p>
 *
 * @see com.catchbug.server.jwt.filter.JwtAuthenticationFilter
 * @see com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler
 * @author younghoCha
 */
public class ModulatedJwtException extends AuthenticationException {
    public ModulatedJwtException(String msg){
        super(msg);
    }
}
