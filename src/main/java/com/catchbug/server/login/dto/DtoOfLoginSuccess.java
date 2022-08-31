package com.catchbug.server.login.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfLoginSuccess</h1>
 * <p>
 *     Success Of Login DTO
 * </p>
 * <p>
 *     로그인 성공 후, 응답될 DTO
 * </p>
 *
 * @see com.catchbug.server.login.LoginService
 * @see com.catchbug.server.login.LoginController
 * @author younghoCha
 */
@Getter
public class DtoOfLoginSuccess {

    /**
     * 멤버 id
     */
    private Long id;
    /**
     * 자체 발급 엑세스 토큰
     */
    private String accessToken;

    /**
     * 자체 발급 리프레시 토큰
     */
    private String refreshToken;

    /**
     * 사용자의 성별
     */
    private Gender gender;

    /**
     * 사용자의 닉네임
     */
    private String nickName;

    @Builder
    public DtoOfLoginSuccess(Long id, String nickname, String accessToken, String refreshToken, Gender gender) {
        this.id = id;
        this.nickName = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.gender = gender;
    }
}
