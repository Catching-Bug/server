package com.catchbug.server.jwt;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String accessToken);

    Optional<com.catchbug.server.jwt.RefreshToken> findByRefreshToken(String refreshToken);
}
