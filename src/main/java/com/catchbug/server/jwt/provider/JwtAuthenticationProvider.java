package com.catchbug.server.jwt.provider;

import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.exception.ModulatedJwtException;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.jwt.util.JwtProvider;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProvider jwtProvider;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사전 처리한 token 얻기
        String token = (String) authentication.getPrincipal();
        try {
            log.info("9");
            DtoOfUserDataFromJwt userPayloads = jwtProvider.getUserData(token);
            log.info("10");
            UserContext context = new UserContext(userPayloads);
            log.info("11");
            return new DtoOfJwtPostAuthenticationToken(context);

        } catch (SignatureException | MalformedJwtException | MissingClaimException | UnsupportedJwtException ex) {
            // JWT 인증 예외
            throw new ModulatedJwtException("잘못된 토큰입니다.");

        } catch (ExpiredJwtException ex) {
            // JWT 만료 예외
            throw new com.catchbug.server.jwt.exception.ExpiredJwtException("만료된 토큰입니다.");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DtoOfJwtPostAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
