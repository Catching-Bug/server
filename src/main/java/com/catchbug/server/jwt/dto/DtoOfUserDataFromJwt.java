package com.catchbug.server.jwt.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfUserDataFromJwt</h1>
 * <p>
 *     Object for Security context
 * </p>
 * <p>
 *     Security Context에 담길 객체
 * </p>
 *
 * @see com.catchbug.server.jwt.filter.JwtAuthenticationFilter
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfUserDataFromJwt {
    /**
     * jwt subject
     */
    private Long id;

    /**
     * jwt payload 내에 존재하는 요청자 nickname
     */
    private String nickname;

    /**
     * jwt payload 내에 존재하는 요청자 성별
     */
    private Gender gender;
}
