package com.catchbug.server.jwt.util;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.properties.JwtProperties;
import com.catchbug.server.member.Gender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * <h1>JwtProvider</h1>
 * <p>
 *     verify, getData Util
 * </p>
 * <p>
 *     Jwt 의 검증 및 decoding 메서드
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtService
 * @author younghoCha
 */
@Component
public class JwtProvider {

    /**
     * 엑세스 토큰 키
     */
    String accessTokenKey = "sample";

    /**
     * 리프레시 토큰 키
     */
    String refreshTokenKey = "sample";

    /**
     * 엑세스 토큰 검증 메서드
     * @param accessToken : 요청자로부터 전달받은 엑세스 토큰
     */
    public void authenticateAccessToken(String accessToken){



    public void authenticateAccessToken(String accessToken){
        Jwts.parser()
                .setSigningKey(jwtProperties.getAccessTokenKey().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * 리프레시 토큰 검증 메서드
     * @param refreshToken : 요청자로부터 받은 리프레시 토큰
     */
    public void authenticateRefreshToken(String refreshToken){
        Jwts.parser().setSigningKey(jwtProperties.getRefreshTokenKey().getBytes(StandardCharsets.UTF_8)).parseClaimsJws(refreshToken).getBody();
    }

    /**
     * 엑세스 토큰의 PayLoads 를 얻는 메서드
     * @param accessToken : 요청자로부터 받은 엑세스 토큰
     * @return 토큰에 포함된 Payloads
     */
    public DtoOfUserDataFromJwt getUserData(String accessToken){

        Claims claims = getClaims(accessToken, jwtProperties.getAccessTokenKey());
        Gender gender = Gender.NONE;
        if(claims.get("gender").equals("male")){
            gender = Gender.MALE;
        }

        if(claims.get("gender").equals("female")){
            gender = Gender.FEMALE;
        }

        return DtoOfUserDataFromJwt.builder()
                .id(Long.parseLong(claims.getSubject()))
                .gender(gender)
                .nickname(claims.get("nickname").toString())
                .build();


    }

    /**
     * 리프레시 토큰을 갱신해야하는지 판별하는 메서드
     * @param refreshToken : 요청자에게 전달받은 리프레시 토큰
     * @param time : 리프레시 토큰 유효기간
     * @return true일 경우 리프레시 필요, false일 경우 리프레시 불 필요
     */
    public boolean checkRenewRefreshToken(String refreshToken, Long time){

        Instant now = Instant.now();
        Instant expiredTime = getClaims(refreshToken, jwtProperties.getRefreshTokenKey()).getExpiration().toInstant();

        long diffTIme = now.until(expiredTime, ChronoUnit.DAYS);

        return diffTIme < time;
    }

    /**
     * 토큰으로부터 Claims 객체를 얻기위한 메서드
     * @param token : JWT
     * @param tokenKey : JWT key
     * @return JWT claims
     */
    private Claims getClaims(String token, String tokenKey){
        return Jwts.parser()
                .setSigningKey(tokenKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
