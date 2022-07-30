package com.catchbug.server.login.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DtoOfLoginSuccess {

    private String accessToken;
    private String refreshToken;
    private Gender gender;
    private String nickName;

    @Builder
    public DtoOfLoginSuccess(String nickname, String tokenType, String accessToken, String refreshToken, Gender gender) {

        this.nickName = nickname;
        this.accessToken = "accessToken";
        this.refreshToken = "refreshToken";
        this.gender = gender;
    }
}
