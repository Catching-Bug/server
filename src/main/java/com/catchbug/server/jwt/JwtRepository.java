package com.catchbug.server.jwt;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h1>JwtRepository</h1>
 * <p>
 *     DAO of JWT Entity
 * </p>
 * <p>
 *     JWT 엔티티에 대한 DAO 객체 클래스
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtService
 * @author younghoCha
 */
public interface JwtRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * 리프레시 토큰이 발급되었는지 확인하는 메서드
     * @param accessToken : 사용자에게 발급한 자체 서비스 엑세스 토큰
     * @return 존재 시 true, 미존재 시 false
     */
    boolean existsByRefreshToken(String accessToken);

    /**
     * 리프레시 토큰을 통해 리프레시 토큰 엔티티를 조회하는 메서드
     * @param refreshToken : 사용자에게 받은 레프레시 토큰
     * @return 조회된 리프레시 토큰 엔티티
     */
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
