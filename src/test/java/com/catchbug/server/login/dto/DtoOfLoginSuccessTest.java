package com.catchbug.server.login.dto;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.member.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DtoOfLoginSuccessTest {

    @DisplayName("Login테스트")
    @Test
    public void loginTest() throws Exception{
        String nickname = "abc";
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        Gender gender = Gender.MALE;
        //given
        DtoOfLoginSuccess loginSuccess = DtoOfLoginSuccess.builder().nickname("abc").accessToken("accessToken")
                .refreshToken("refreshToken").gender(Gender.MALE).build();
        //when

        //then

        Assertions.assertEquals(nickname, loginSuccess.getNickName());
        Assertions.assertEquals(accessToken, loginSuccess.getAccessToken());
        Assertions.assertEquals(refreshToken, loginSuccess.getRefreshToken());
        Assertions.assertEquals(gender, loginSuccess.getGender());

    }

}
