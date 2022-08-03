package com.catchbug.server.login;

import com.catchbug.server.jwt.JwtService;
import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.catchbug.server.oauth2.util.Oauth2Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = LoginService.class)
public class LoginServiceTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private Oauth2Util oauth2Util;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private LoginService loginService;

    @DisplayName("로그인에 성공한다. ")
    @Test
    public void login_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        DtoOfJwt dtoOfJwt = DtoOfJwt.builder()
                .accessToken("accessToken")
                        .refreshToken("refreshToken")
                                .build();
        DtoOfOauthTokenResponse mockDtoOfOauthResponse = DtoOfOauthTokenResponse.builder().accessToken("a").refreshToken("b").build();
        DtoOfUserProfile mockDtoOfUserProfile = new DtoOfUserProfile();
        //when & mocking
        given(oauth2Util.getToken(anyString())).willReturn(DtoOfOauthTokenResponse.builder().accessToken("a").refreshToken("b").build());
        given(oauth2Util.getUserProfile(anyString())).willReturn(mockDtoOfUserProfile);
        given(jwtService.createTokenDto(member)).willReturn(dtoOfJwt);
        given(memberService.login(any())).willReturn(member);
        DtoOfLoginSuccess loginSuccess = loginService.login("abc");

        //then
        Assertions.assertEquals(member.getGender(), loginSuccess.getGender());
        Assertions.assertEquals(member.getNickname(), loginSuccess.getNickName());
        Assertions.assertEquals(dtoOfJwt.getAccessToken(), loginSuccess.getAccessToken());
        Assertions.assertEquals(dtoOfJwt.getRefreshToken(), loginSuccess.getRefreshToken());
    }



}
