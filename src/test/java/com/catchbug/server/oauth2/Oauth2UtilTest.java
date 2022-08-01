package com.catchbug.server.oauth2;

import com.catchbug.server.member.Gender;
import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.catchbug.server.oauth2.util.Oauth2RequestUtil;
import com.catchbug.server.oauth2.util.Oauth2Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class Oauth2UtilTest {

    @MockBean
    private Oauth2RequestUtil oauth2RequestUtil;

    @Autowired
    private Oauth2Util oauth2Util;

    @DisplayName("카카오 서버에 AccessToken을 정상적으로 응답받는다.")
    @Test
    public void getAccessToken_from_kakao_OnSuccess() throws Exception{
        //given
        DtoOfOauthTokenResponse expectedResponse = DtoOfOauthTokenResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken").build();

        String code = "sampleCode";

        //when
        given(oauth2RequestUtil.requestAuth(any())).willReturn(expectedResponse);
        DtoOfOauthTokenResponse actualResponse = oauth2Util.getToken(code);

        //then
        Assertions.assertEquals(expectedResponse.getAccessToken(), actualResponse.getAccessToken());
        Assertions.assertEquals(expectedResponse.getRefreshToken(), actualResponse.getRefreshToken());

    }

    @DisplayName("카카오 서버에서 정상적으로 User Profile을 받는다.")
    @Test
    public void getProfile_from_kakao_OnSuccess() throws Exception{
        //given
        String accessToken = "accessToken";
        DtoOfUserProfile userProfile = setUpSampleProfile(Gender.MALE);

        //when
        given(oauth2RequestUtil.requestProfile(accessToken)).willReturn(userProfile);
        DtoOfUserProfile actualProfile = oauth2Util.getUserProfile(accessToken);

        //then

        Assertions.assertEquals("테스트아이디", actualProfile.getKakaoAccount().getProfile().getNickname());
        Assertions.assertEquals("테스트아이디", actualProfile.getProperties().getNickname());
        Assertions.assertEquals(123456L, actualProfile.getId());
        Assertions.assertEquals(Gender.MALE, actualProfile.getKakaoAccount().getGender());

    }

    @DisplayName("카카오 서버로부터 성별 항목을 받지 못하였을 때 성별은 None이 되어야 한다.")
    @Test
    public void not_Be_Provided_Gender_Data() throws Exception{

        //given
        String accessToken = "accessToken";
        DtoOfUserProfile userProfile = setUpSampleProfile(null);

        //when
        given(oauth2RequestUtil.requestProfile(accessToken)).willReturn(userProfile);
        DtoOfUserProfile actualProfile = oauth2Util.getUserProfile(accessToken);

        //then
        Assertions.assertEquals("테스트아이디", actualProfile.getKakaoAccount().getProfile().getNickname());
        Assertions.assertEquals("테스트아이디", actualProfile.getProperties().getNickname());
        Assertions.assertEquals(123456L, actualProfile.getId());
        Assertions.assertEquals(Gender.NONE, actualProfile.getKakaoAccount().getGender());
    }

    public static DtoOfUserProfile setUpSampleProfile(Gender gender){
        DtoOfUserProfile.Profile sampleProfile = new DtoOfUserProfile.Profile();
        sampleProfile.setNickname("테스트아이디");

        DtoOfUserProfile.KakaoAccount sampleKakaoAccount = new DtoOfUserProfile.KakaoAccount();
        sampleKakaoAccount.setProfile(sampleProfile);
        sampleKakaoAccount.setGender(gender);

        DtoOfUserProfile.Properties sampleProperties = new DtoOfUserProfile.Properties();
        sampleProperties.setNickname("테스트아이디");

        DtoOfUserProfile userProfile = new DtoOfUserProfile();
        userProfile.setId(123456L);
        userProfile.setKakaoAccount(sampleKakaoAccount);
        userProfile.setProperties(sampleProperties);

        return userProfile;
    }


}


