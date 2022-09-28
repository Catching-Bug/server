package com.catchbug.server.jwt.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfJwt</h1>
 * <p>
 *     Response of login
 * </p>
 * <p>
 *     로그인 후 응답되는 JWT Dto
 * </p>
 *
 * @see com.catchbug.server.login.LoginController
 * @see com.catchbug.server.login.LoginService
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfJwt {
    /**
     * 자체 서비스 발급 엑세스 토큰
     */
    private String accessToken;

    /**
     * 자체 서비스 발급 리프레시 토큰
     */
    private String refreshToken;
}
