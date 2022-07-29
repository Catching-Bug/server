package com.catchbug.server.login.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfLoginSuccess {

    private String accessToken;
    private String refreshToken;
    private Gender gender;
    private String nickName;
}
