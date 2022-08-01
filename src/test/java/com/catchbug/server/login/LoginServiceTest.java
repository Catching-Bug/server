package com.catchbug.server.login;

import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.catchbug.server.oauth2.util.Oauth2Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
//    @InjectMocks
//    LoginService loginService;
//
//    @Mock
//    Oauth2Util oauth2Util;
//
//    @Mock
//    MemberService memberService;
//
//
//    @DisplayName("카카오로부터 Access Token 을 정상적으로 받았다.")
//    @Test
//    public void getAccessToken_OnSuccess() throws Exception{
//
//        //given
//        String code = "abc";
//        Member memberEntity = Member.builder()
//                .nickname("테스트아이디")
//                .gender(Gender.MALE)
//                .build();
//        DtoOfLoginSuccess expectedLoginDto = DtoOfLoginSuccess.builder()
//                .nickname("테스트아이디")
//                .accessToken("abc")
//                .refreshToken("abc")
//                .gender(Gender.MALE).build();
//
////        doReturn(new DtoOfOauthTokenResponse()).when(oauth2Util.getToken(code));
////        doReturn(new DtoOfUserProfile()).when(oauth2Util.getUserProfile(anyString()));
////        doReturn(memberEntity).when(memberService.login(new DtoOfUserProfile()));
//
////        when(oauth2Util.getToken(code)).thenReturn(new DtoOfOauthTokenResponse());
////        when(oauth2Util.getUserProfile(anyString())).thenReturn(new DtoOfUserProfile());
//        when(memberService.login(new DtoOfUserProfile())).thenReturn(memberEntity);
//
//        //when
//        DtoOfLoginSuccess dtoOfLoginSuccess = loginService.login("123");
//
//        //then
//        Assertions.assertEquals("테스트아이디", dtoOfLoginSuccess.getNickName());
//        Assertions.assertEquals(Gender.MALE, dtoOfLoginSuccess.getGender());
//
//
//    }

}
