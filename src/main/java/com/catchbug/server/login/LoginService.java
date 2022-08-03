package com.catchbug.server.login;

import com.catchbug.server.jwt.JwtService;
import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import com.catchbug.server.oauth2.dto.DtoOfOauthTokenResponse;
import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import com.catchbug.server.oauth2.util.Oauth2Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class LoginService {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final Oauth2Util oauth2Util;

    public DtoOfLoginSuccess login(String authorizationCode) throws JsonProcessingException {

        DtoOfOauthTokenResponse oauthTokenResponse = oauth2Util.getToken(authorizationCode);
        DtoOfUserProfile userProfile = oauth2Util.getUserProfile(oauthTokenResponse.getAccessToken());
        Member memberEntity = memberService.login(userProfile);
        DtoOfJwt dtoOfJwt = jwtService.createTokenDto(memberEntity);
        DtoOfLoginSuccess dtoOfLoginSuccess = DtoOfLoginSuccess.builder()
                .nickname(memberEntity.getNickname())
                .accessToken(dtoOfJwt.getAccessToken())
                .refreshToken(dtoOfJwt.getRefreshToken())
                .gender(memberEntity.getGender())
                .build();

        return dtoOfLoginSuccess;
    }

}
