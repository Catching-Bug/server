package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.util.JwtFactory;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>JwtService</h1>
 * <p>
 *     JWT Business Logic Object
 * </p>
 * <p>
 *     Jwt에 대한 비즈니스 로직이 있는 객체
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtService
 * @author younghoCha
 */
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

    /**
     * 엑세스 토큰 생성 메서드
     * @param member : 요청자 엔티티
     * @return 생성된 엑세스 토큰
     */
    private String createAccessToken(Member member){
        String accessToken = jwtFactory.createAccessToken(member);
        return accessToken;
    }

    /**
     * 리프레시 토큰 생성 메서드
     * @param accessToken : 사용자에게 발급된 자체 서비스 엑세스토큰
     * @param member : 요청자 엔티티
     * @return : 생성된 자체 서비스 리프레시 토큰
     */
    private String createRefreshToken(String accessToken, Member member){

        if(!isExistsRefreshToken(accessToken)){
            String refreshToken = jwtFactory.createRefreshToken(accessToken);
            saveRefreshToken(accessToken, refreshToken, member);
            return refreshToken;
        }

        return accessToken;
    }

    /**
     * JWT 의 payloads 와 subject 의 데이터를 받는 메서드
     * @param accessToken : 사용자에게 발급한 자체 서비스 엑세스 토큰
     * @return 사용자 payloads
     */
    public DtoOfUserDataFromJwt getUserData(String accessToken){
        DtoOfUserDataFromJwt dtoOfUserDataFromJwt =
                jwtProvider.getUserData(accessToken);
        return dtoOfUserDataFromJwt;
    }

    /**
     * 엑세스 토큰 갱신을 위한 메서드
     * @param refreshToken : 사용자에게 발급된 리프레시 토큰
     * @return  갱신된 토큰 DTO
     */
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

    /**
     * 리프레시 토큰의 발급 유무 확인 메서드
     * @param refreshToken : 발급된 리프레시 토큰
     * @return 발급 유무 / true 시 발급 / false 시 미발급
     */
    public boolean isExistsRefreshToken(String refreshToken){
        return jwtRepository.existsByRefreshToken(refreshToken);
    }

    /**
     * 생성된 토큰을 저장하는 메서드
     * @param accessToken : 사용자에게 발급한 자체 서비스 엑세스 토큰
     * @param refreshToken : 사용자에게 발급한 자체 서비스 리프레시 토큰
     * @param member : 요청자 엔티티
     */
    public void saveRefreshToken(String accessToken, String refreshToken, Member member){

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)

                .build();

        jwtRepository.save(refreshTokenEntity);
    }

    /**
     * 리프레시 토큰 엔티티를 DB로 부터 얻는 메서드
     * @param refreshToken : 사용자에게 발급한 자체 서비스 레프레시 토큰
     * @return DB 에서 얻은 리프레시 토큰 엔티티
     */
    private RefreshToken getRefreshToken(String refreshToken){
        RefreshToken refreshTokenEntity = jwtRepository.findByRefreshToken(refreshToken).get();

        return refreshTokenEntity;
    }



}
