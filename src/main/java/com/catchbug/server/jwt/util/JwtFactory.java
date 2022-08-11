package com.catchbug.server.jwt.util;

import com.catchbug.server.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * <h1>JwtFactory</h1>
 * <p>
 *     creating JWT Object
 * </p>
 * <p>
 *     JWT를 생성하는 클래스
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtService
 * @author younghoCha
 */
@Component
public class JwtFactory {

    /**
     * JWT 의 엑세스 토큰 키
     */
    private String ACCESS_TOKEN_SECRET_KEY = "sample";

    /**
     * JWT 의 리프레시 토큰 키
     */
    private String REFRESH_TOKEN_SECRET_KEY = "sample";

    /**
     * 엑세스 토큰 유효 기간
     */
    private Long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L; // 30 minutes

    /**
     * 리프레시 토큰 유효 기간
     */
    private Long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; //30 days

    /**
     * Base64로 인코딩된 엑세스 토큰 키
     */
    private String encodedAccessKey;

    /**
     * Base64로 인코딩된 리프레시 토큰 키
     */
    private String encodedRefreshKey;

    /**
     * 인코딩을 하기위한 메서드
     */
    @PostConstruct
    protected void init(){
        encodedAccessKey = Base64.getEncoder().encodeToString(this.ACCESS_TOKEN_SECRET_KEY.getBytes());
        encodedRefreshKey = Base64.getEncoder().encodeToString(this.REFRESH_TOKEN_SECRET_KEY.getBytes());
    }

    /**
     * 엑세스 토큰을 생성하기 위한 메서드
     * @param member : 요청자 엔티티
     * @return 생성된 엑세스 토큰
     */
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

    /**
     * 리프레시 토큰 생성 메서드
     * @param accessToken : 생성된 엑세스 토큰
     * @return : 생성된 레프레시토큰
     */
    public String createRefreshToken(String accessToken){
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.REFRESH_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.encodedRefreshKey)
                .compact();
    }


}