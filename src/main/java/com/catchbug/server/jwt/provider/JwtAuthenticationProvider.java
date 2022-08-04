package com.catchbug.server.jwt.provider;

import com.catchbug.server.jwt.JwtService;
import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.jwt.util.JwtProvider;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProvider jwtProvider;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사전 처리한 token 얻기
        String token = (String) authentication.getPrincipal();
        try {

            DtoOfUserDataFromJwt userPayloads = jwtProvider.getUserData(token);
            UserContext context = new UserContext(userPayloads);

            return new DtoOfJwtPostAuthenticationToken(context);

        } catch (SignatureException | MalformedJwtException | MissingClaimException ex) {

            // JWT 인증 예외
            throw new RuntimeException();

        } catch (ExpiredJwtException ex) {

            // JWT 만료 예외
            throw new RuntimeException();
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return DtoOfJwtPostAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
