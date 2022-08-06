package com.catchbug.server.jwt.util;

import com.catchbug.server.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtFactory {

    private String ACCESS_TOKEN_SECRET_KEY = "sample";
    private String REFRESH_TOKEN_SECRET_KEY = "sample";

    private Long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L; // 30 minutes

    private Long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; //30 days

    private String encodedAccessKey;
    private String encodedRefreshKey;

    @PostConstruct
    protected void init(){
        encodedAccessKey = Base64.getEncoder().encodeToString(this.ACCESS_TOKEN_SECRET_KEY.getBytes());
        encodedRefreshKey = Base64.getEncoder().encodeToString(this.REFRESH_TOKEN_SECRET_KEY.getBytes());
    }

    public String createAccessToken(Member member){

        Claims claims = Jwts.claims().setSubject(member.getId().toString());
        claims.put("nickname", member.getNickname());
        claims.put("gender", member.getGender());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.encodedAccessKey)
                .compact();
    }

    public String createRefreshToken(String accessToken){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.encodedRefreshKey)
                .compact();
    }


}
