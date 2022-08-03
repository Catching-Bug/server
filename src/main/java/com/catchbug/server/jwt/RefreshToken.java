package com.catchbug.server.jwt;

import com.catchbug.server.member.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "GENDER")
    private Gender gender;

    public void updateAccessToken(String accessToken){

        this.accessToken = accessToken;

    }
}
