package com.catchbug.server.jwt.util;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Gender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    String accessTokenKey = "sample";
    String refreshTokenKey = "sample";
    public void authenticateAccessToken(String accessToken){


        Jwts.parser()
                .setSigningKey(accessTokenKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public void authenticateRefreshToken(String refreshToken){
        Jwts.parser().setSigningKey(refreshTokenKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(refreshToken).getBody();
    }

    public DtoOfUserDataFromJwt getUserData(String accessToken){

        Claims claims = getClaims(accessToken, this.accessTokenKey);
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

    public boolean checkRenewRefreshToken(String refreshToken, Long time){


        Instant now = Instant.now();
        Instant expiredTime = getClaims(refreshToken, this.refreshTokenKey).getExpiration().toInstant();

        long diffTIme = now.until(expiredTime, ChronoUnit.DAYS);

        return diffTIme < time;
    }

    private Claims getClaims(String token, String tokenKey){
        return Jwts.parser()
                .setSigningKey(tokenKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
