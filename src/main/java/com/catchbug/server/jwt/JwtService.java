package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

    //private final Long REFRESH_CHECK_TIME = ;
    private final JwtRepository jwtRepository;
    private final JwtFactory jwtFactory;
    private final JwtProvider jwtProvider;

    // 최초 로그인
    public DtoOfJwt createTokenDto(Member member){
        String accessToken = createAccessToken(member);

        String refreshToken = createRefreshToken(accessToken, member);

        return DtoOfJwt.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    private String createAccessToken(Member member){
        String accessToken = jwtFactory.createAccessToken(member);
        return accessToken;
    }

    private String createRefreshToken(String accessToken, Member member){

        if(!isExistsRefreshToken(accessToken)){
            String refreshToken = jwtFactory.createRefreshToken(accessToken);
            saveRefreshToken(accessToken, refreshToken, member);
            return refreshToken;
        }

        return accessToken;
    }
    // 정보 얻기
    public DtoOfUserDataFromJwt getUserData(String accessToken){
        DtoOfUserDataFromJwt dtoOfUserDataFromJwt =
                jwtProvider.getUserData(accessToken);
        return dtoOfUserDataFromJwt;
    }
    // Token Refresh
    public DtoOfJwt refresh(String refreshToken){
        RefreshToken refreshTokenEntity = getRefreshToken(refreshToken);
        //fixme 하드코딩 고쳐야함
        boolean check = jwtProvider.checkRenewRefreshToken(refreshTokenEntity.getRefreshToken(), 3L);
        Member member = Member.builder()
                .gender(refreshTokenEntity.getGender())
                .nickname(refreshTokenEntity.getNickname())
                .build();

        if(check){
            DtoOfJwt dtoOfJwt = createTokenDto(member);
            jwtRepository.delete(refreshTokenEntity);

            return dtoOfJwt;
        }

        String renewAccessToken = createAccessToken(member);
        refreshTokenEntity.updateAccessToken(renewAccessToken);

        return DtoOfJwt.builder().
                refreshToken(refreshTokenEntity.getRefreshToken())
                .accessToken(renewAccessToken)
                .build();
    }


    public boolean isExistsRefreshToken(String refreshToken){
        return jwtRepository.existsByRefreshToken(refreshToken);
    }

    public void saveRefreshToken(String accessToken, String refreshToken, Member member){

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)

                .build();

        jwtRepository.save(refreshTokenEntity);
    }

    private RefreshToken getRefreshToken(String refreshToken){
        RefreshToken refreshTokenEntity = jwtRepository.findByRefreshToken(refreshToken).get();

        return refreshTokenEntity;
    }



}
