package com.catchbug.server.login.dto;

import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DtoOfOauthTokenResponseTest {

    @DisplayName("Oauth2 Token Response dto 테스트")
    @Test
    public void oauth2_response_token_OnSuccess() throws Exception{

        //given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        //when
        DtoOfOauthTokenResponse dtoOfOauthTokenResponse =
                DtoOfOauthTokenResponse.builder().accessToken("accessToken").refreshToken("refreshToken")
                        .build();

        //then
        Assertions.assertEquals(accessToken, dtoOfOauthTokenResponse.getAccessToken());
        Assertions.assertEquals(refreshToken, dtoOfOauthTokenResponse.getRefreshToken());

    }

}
