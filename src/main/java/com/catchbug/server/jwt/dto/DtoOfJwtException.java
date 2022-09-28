package com.catchbug.server.jwt.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfException</h1>
 * <p>
 *     Message Dto from Jwt exception
 * </p>
 * <p>
 *     JWT 예외 시 body 내에 응답될 메세지
 * </p>
 *
 * @see com.catchbug.server.jwt.filter.JwtAuthenticationFilter
 * @see com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfJwtException {

    /**
     * jwt 예외 응답 메세지
     */
    private String msg;
}
