package com.catchbug.server.jwt.provider;

import com.catchbug.server.jwt.dto.DtoOfJwtAuthentication;
import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.exception.ModulatedJwtException;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = JwtAuthenticationProvider.class)
public class JwtAuthenticationProviderTest {

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;


    @DisplayName("토큰이 정상적으로 인증되었을 때, 정상적인 Authentication 객체가 리턴되어야 한다.")
    @Test
    public void authenticate_OnSuccess() throws Exception{

        //given
        Member sampleMember = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String accessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        DtoOfUserDataFromJwt sampleData = getSampleUserData(sampleMember);
        UserContext userContext = getSampleUserContext(sampleData);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

            // 예상되는 반환 값
        DtoOfJwtPostAuthenticationToken expectedData = new DtoOfJwtPostAuthenticationToken(userContext);

        //mocking
        given(jwtProvider.getUserData(accessToken)).willReturn(sampleData);

        //when
        DtoOfJwtPostAuthenticationToken actualData = (DtoOfJwtPostAuthenticationToken) jwtAuthenticationProvider.authenticate(preAuthentication);

        //then
        Assertions.assertEquals(expectedData.getUserContext().getUsername(), actualData.getUserContext().getUsername());

    }

    @DisplayName("토큰 만료 시, Expired 예외가 발생하고 커스텀한 Expired 예외가 발생해야한다.")
    @Test
    public void expired_Exception() throws Exception{

        //given
        Member sampleMember = setUpMember();
        Long validTime = 0L;

        String preAccessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        String accessToken = preAccessToken.substring(0, preAccessToken.length() -1);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

        //mocking
        given(jwtProvider.getUserData(accessToken)).willThrow(ExpiredJwtException.class);

        //then
        //todo 예외 발생 시, http response 값을 응답하여 검증해야함 지금은 Exception 처리가 안되어있음
        Assertions.assertThrows(com.catchbug.server.jwt.exception.ExpiredJwtException.class, ()-> {jwtAuthenticationProvider.authenticate(preAuthentication);});

    }

    @DisplayName("토큰 변조 시, 토큰 예외가 발생하고 커스텀한 예외가 다시 한번 발생해야 한다.")
    @Test
    public void check_malformedJwtException() throws Exception{

        //given & mocking
        Member sampleMember = setUpMember();
        Long validTime = 0L;

        String preAccessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        String accessToken = preAccessToken.substring(0, preAccessToken.length() -1);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

        given(jwtProvider.getUserData(accessToken)).willThrow(MalformedJwtException.class);
        //when
        //then
        Assertions.assertThrows(ModulatedJwtException.class, ()-> {jwtAuthenticationProvider.authenticate(preAuthentication);});

    }
    @DisplayName("토큰 변조 시, 토큰 예외가 발생하고 커스텀한 예외가 다시 한번 발생해야 한다.")
    @Test
    public void check_SignatureJwtException() throws Exception{

        //given & mocking
        Member sampleMember = setUpMember();
        Long validTime = 0L;

        String preAccessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        String accessToken = preAccessToken.substring(0, preAccessToken.length() -1);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

        given(jwtProvider.getUserData(accessToken)).willThrow(SignatureException.class);
        //when
        //then
        Assertions.assertThrows(ModulatedJwtException.class, ()-> {jwtAuthenticationProvider.authenticate(preAuthentication);});

    }

    @DisplayName("토큰 변조 시, 토큰 예외가 발생하고 커스텀한 예외가 다시 한번 발생해야 한다.")
    @Test
    public void check_UnsupportedJwtException() throws Exception{

        //given & mocking
        Member sampleMember = setUpMember();
        Long validTime = 0L;

        String preAccessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        String accessToken = preAccessToken.substring(0, preAccessToken.length() -1);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

        given(jwtProvider.getUserData(accessToken)).willThrow(UnsupportedJwtException.class);
        //when
        //then
        Assertions.assertThrows(ModulatedJwtException.class, ()-> {jwtAuthenticationProvider.authenticate(preAuthentication);});

    }

    @DisplayName("토큰 변조 시, 토큰 예외가 발생하고 커스텀한 예외가 다시 한번 발생해야 한다.")
    @Test
    public void check_missingClaimJwtException() throws Exception{

        //given & mocking
        Member sampleMember = setUpMember();
        Long validTime = 0L;

        String preAccessToken = setUpToken(sampleMember, validTime, JwtProviderTest.TokenType.ACCESS);
        String accessToken = preAccessToken.substring(0, preAccessToken.length() -1);
        DtoOfJwtAuthentication preAuthentication = getPreAuthentication(accessToken);

        given(jwtProvider.getUserData(accessToken)).willThrow(MissingClaimException.class);
        //when
        //then
        Assertions.assertThrows(ModulatedJwtException.class, ()-> {jwtAuthenticationProvider.authenticate(preAuthentication);});

    }


    public UserContext getSampleUserContext(DtoOfUserDataFromJwt sampleData){
        return new UserContext(sampleData);
    }

    public DtoOfUserDataFromJwt getSampleUserData(Member sampleMember){
        DtoOfUserDataFromJwt sampleData = DtoOfUserDataFromJwt.builder()
                .id(sampleMember.getId())
                .gender(sampleMember.getGender())
                .nickname(sampleMember.getNickname())
                .build();
        return sampleData;
    }

    public DtoOfJwtAuthentication getPreAuthentication(String accessToken){
        return new DtoOfJwtAuthentication(accessToken);
    }









}
