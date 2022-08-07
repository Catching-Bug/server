package com.catchbug.server.jwt;

import com.catchbug.server.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * <h1>RefreshToken</h1>
 * <p>
 *     RefreshToken Entity
 * </p>
 * <p>
 *     리프레시 토큰 엔티티 객체
 * </p>
 *
 * @see com.catchbug.server.member.MemberRepository
 * @author younghoCha
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    /**
     * id(pk)
     */
    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 자체 발급 엑세스 토큰
     */
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    /**
     * 자체 발급 리프레시 토큰
     */
    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    /**
     * 토큰을 발급받은 사용자
     */
    @Column(name = "MEMBER_ID")
    private Long memberId;

    /**
     * 토큰을 발급받은 사용자 닉네임
     */
    @Column(name = "NICKNAME")
    private String nickname;

    /**
     * 토큰을 발급받은 사용자 성별
     */
    @Column(name = "GENDER")
    private Gender gender;

    public void updateAccessToken(String accessToken){

        this.accessToken = accessToken;

    }
}
