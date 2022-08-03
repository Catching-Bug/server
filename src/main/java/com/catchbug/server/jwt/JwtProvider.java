package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Gender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtProvider {

    String accessTokenKey = "sample";
    String refreshTokenkey = "sample";
    public void authenticateAccessToken(String accessToken){
        Jwts.parser().setSigningKey(accessTokenKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(accessToken).getBody();
    }

    public void authenticateRefreshToken(String refreshToken){
        Jwts.parser().setSigningKey(refreshTokenkey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(refreshToken).getBody();
    }

    public DtoOfUserDataFromJwt getUserData(String accessToken){

        Claims claims = Jwts.parser().setSigningKey(accessTokenKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(accessToken).getBody();
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
}
