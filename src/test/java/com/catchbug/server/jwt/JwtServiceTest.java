package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.util.JwtFactory;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@SpringBootTest(classes = JwtService.class)
public class JwtServiceTest {
    @MockBean
    private JwtFactory jwtFactory;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private JwtRepository jwtRepository;

    @Autowired
    private JwtService jwtService;

    private final String REFRESH_KEY = "sample";

    @DisplayName("AccessToken을 정상적으로 생성한다.")
    @Test
    public void create_accessToken_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        given(jwtFactory.createAccessToken(member)).willReturn(accessToken);
        given(jwtFactory.createRefreshToken(accessToken)).willReturn(refreshToken);
        given(jwtRepository.save(any())).willReturn(null);
        //when
        DtoOfJwt dtoOfJwt = jwtService.createTokenDto(member);

        //then
        Assertions.assertEquals(accessToken, dtoOfJwt.getAccessToken());
        Assertions.assertEquals(refreshToken, dtoOfJwt.getRefreshToken());

    }

    @DisplayName("AccessToken payloads에 있는 유저의 정보를 올바르게 받아온다.")
    @Test
    public void getPayloads_from_accessToken() throws Exception{

        //given
        //when
        String accessToken = "abc";
        DtoOfUserDataFromJwt expectedData =
                DtoOfUserDataFromJwt.builder().id(1L).gender(Gender.MALE).nickname("테스트아이디").build();

        given(jwtProvider.getUserData(accessToken)).willReturn(expectedData);
        DtoOfUserDataFromJwt actualData = jwtService.getUserData(accessToken);

        //then
        Assertions.assertEquals(expectedData.getGender(), actualData.getGender());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getNickname(), actualData.getNickname());

    }


    @DisplayName("RefreshToken 을 통해서 정상적으로 AccessToken 만 Refresh 한다.")
    @Test
    public void refresh_accessToken_OnSuccess() throws Exception{

        //given
        Long validTime = 1000 * 60 * 60 * 24L * 5; // 5일
        Member member = setUpMember();
        String renewedAccessToken = "abc";
        String refreshToken = setUpRefreshToken(this.REFRESH_KEY, validTime);
        RefreshToken sampleRefreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .accessToken("a")
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build();
            //mocking
        given(jwtRepository.findByRefreshToken(any())).willReturn(Optional.of(sampleRefreshTokenEntity));
        given(jwtProvider.checkRenewRefreshToken(any(), any())).willReturn(false);
        given(jwtFactory.createAccessToken(any())).willReturn(renewedAccessToken);

        //when
        DtoOfJwt dtoOfJwt = jwtService.refresh(refreshToken);

        //then
        Assertions.assertEquals(refreshToken, dtoOfJwt.getRefreshToken());
        Assertions.assertEquals(renewedAccessToken, dtoOfJwt.getAccessToken());

    }

    @DisplayName("RefreshToken 을 통해서 RefreshToken, AccessToken 둘 다 Refresh 한다.")
    @Test
    public void refresh_both_token_OnSuccess(){

        //given
        Long validTime = 1000 * 60 * 60 * 24L * 2; // 5일
        Member member = setUpMember();
        String renewedAccessToken = "abc";
        String renewedRefreshToken = "renewRefreshToken";
        String refreshToken = setUpRefreshToken(this.REFRESH_KEY, validTime);
        RefreshToken sampleRefreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .accessToken("a")
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build();
        //mocking
        given(jwtRepository.findByRefreshToken(any())).willReturn(Optional.of(sampleRefreshTokenEntity));
        given(jwtProvider.checkRenewRefreshToken(any(), any())).willReturn(true);
        given(jwtFactory.createAccessToken(any())).willReturn(renewedAccessToken);
        given(jwtFactory.createRefreshToken(renewedAccessToken)).willReturn(renewedRefreshToken);

        //when
        DtoOfJwt dtoOfJwt = jwtService.refresh(refreshToken);

        //then
        Assertions.assertEquals(renewedRefreshToken, dtoOfJwt.getRefreshToken());
        Assertions.assertEquals(renewedAccessToken, dtoOfJwt.getAccessToken());

    }

    private String setUpRefreshToken(String key, Long validTime){
        String encodedKey =  Base64.getEncoder().encodeToString(key.getBytes());
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();
    }


}
