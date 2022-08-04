package com.catchbug.server.jwt.util;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;
import java.util.Date;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;

@Slf4j
@SpringBootTest(classes = JwtProvider.class)
public class JwtProviderTest {

    private static final String SECRET_KEY = "sample";

    @MockBean
    private JwtFactory jwtFactory;

    @Autowired
    private JwtProvider jwtProvider;



//    @DisplayName("올바른 AccessToken은 검증에 통과한다.")
//    @Test
//    public void accessToken_authenticate_onSuccess() throws Exception{
//
//        //given
//        Member member = setUpMember();
//        String accessToken = jwtFactory.createAccessToken(member);
//
//        //when
//        Authentication authentication = jwtProvider.authenticate(accessToken);
//        //then
//
//
//    }

    @DisplayName("변조된 토큰일 경우 SignatureException 예외가 터진다. - AccessToken")
    @Test
    public void authenticate_accessToken_OnSignatureException(){

        //given
        Member member = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String accessToken = setUpToken(member, validTime, TokenType.ACCESS);

        String signaturedToken = accessToken.substring(0, accessToken.length() - 1);

        //when
        Assertions.assertThrows(SignatureException.class, ()-> {jwtProvider.authenticateAccessToken(signaturedToken);});

        //then
    }
    @DisplayName("변조된 토큰일 경우 SignatureException 예외가 터진다. - RefreshToken")
    @Test
    public void authenticate_RefreshToken_OnSignatureException(){

        //given
        Member member = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String refreshToken = setUpToken(member, validTime, TokenType.REFRESH);
        String signaturedToken = refreshToken.substring(0, refreshToken.length() - 1);

        //when
        //then
        Assertions.assertThrows(SignatureException.class, ()-> {jwtProvider.authenticateRefreshToken(signaturedToken);});
    }

    @DisplayName("AccessToken body값을 정상적으로 가지고 온다.")
    @Test
    public void getBody_from_accessToken(){

        //given
        Member member = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String accessToken = setUpToken(member, validTime, TokenType.ACCESS);

        //when
        //then
        DtoOfUserDataFromJwt dtoOfUserData = jwtProvider.getUserData(accessToken);

        Assertions.assertEquals(member.getId(), dtoOfUserData.getId());
        Assertions.assertEquals(member.getNickname(), dtoOfUserData.getNickname());
        Assertions.assertEquals(member.getGender(), dtoOfUserData.getGender());


    }





    @DisplayName("RefreshToken 유효기간이 3일 이하이면 갱신이 필요하다는 true를 리턴한다.")
    @Test
    public void check_renew_refreshToken_OnRenew() throws Exception{

        //given
        Member member = setUpMember();
        Long validTime = 1000 * 60 * 60 * 24L; // 24시간

        String refreshToken = setUpToken(member, validTime, TokenType.ACCESS);

        //when
        boolean required_renew = jwtProvider.checkRenewRefreshToken(refreshToken, 3L);

        //then
        Assertions.assertTrue(required_renew);

    }
    @DisplayName("RefreshToken 유효기간이 3일 이상이면 갱신이 필요없다는 false를 리턴한다.")
    @Test
    public void check_renew_refreshToken_OnNotRenew() throws Exception{

        //given
        Member member = setUpMember();
        Long validTime = 1000 * 60 * 60 * 24L * 5; // 5일

        String refreshToken = setUpToken(member, validTime, TokenType.ACCESS);

        //when
        boolean required_renew = jwtProvider.checkRenewRefreshToken(refreshToken, 3L);

        //then
        Assertions.assertFalse(required_renew);

    }


    public static String setUpToken(Member member, Long validTime, TokenType tokenType){
        String encodedKey =  Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        if(tokenType.equals(TokenType.REFRESH)){
            Date now = new Date();
            return Jwts.builder()
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + validTime))
                    .signWith(SignatureAlgorithm.HS256, encodedKey)
                    .compact();
        }
        Claims claims = Jwts.claims().setSubject(member.getId().toString());
        claims.put("nickname", member.getNickname());
        claims.put("gender", member.getGender());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();

    }


    public enum TokenType{
        REFRESH,ACCESS
    }



}
