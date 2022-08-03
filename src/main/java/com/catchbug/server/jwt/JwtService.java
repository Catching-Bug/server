package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtRepository jwtRepository;
    private final JwtFactory jwtFactory;
    private final JwtProvider jwtProvider;
    public DtoOfJwt createToken(Member member){
        String accessToken = createAccessToken(member);

        String refreshToken = createRefreshToken(accessToken);

        return DtoOfJwt.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public String createAccessToken(Member member){
        String accessToken = jwtFactory.createAccessToken(member);
        return accessToken;
    }

    public String createRefreshToken(String accessToken){

        if(!isExistsRefreshToken(accessToken)){
            String refreshToken = jwtFactory.createRefreshToken(accessToken);
            saveRefreshToken(accessToken, refreshToken);
            return refreshToken;
        }
        
        return accessToken;
    }
    public DtoOfUserDataFromJwt getUserData(String accessToken){
        DtoOfUserDataFromJwt dtoOfUserDataFromJwt =
                jwtProvider.getUserData(accessToken);
        return dtoOfUserDataFromJwt;
    }

    public boolean isExistsRefreshToken(String refreshToken){
        return jwtRepository.existsByRefreshToken(refreshToken);
    }

    public void renewalRefreshToken(String refreshToken){
        RefreshToken refreshTokenEntity = jwtRepository.findByRefreshToken(refreshToken).get();
        refreshTokenEntity.updateRefreshToken(createRefreshToken(refreshTokenEntity.getAccessToken()));
    }

    public void saveRefreshToken(String accessToken, String refreshToken){

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        jwtRepository.save(refreshTokenEntity);
    }

    public boolean checkRenewalRefreshToken(String refreshToken){
        return false;
    }


}
