package com.catchbug.server.jwt.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfUserDataFromJwt {
    private Long id;
    private String nickname;
    private Gender gender;
}
