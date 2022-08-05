package com.catchbug.server.board.mock_auth;

import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.member.Gender;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {

        String nickname = annotation.nickname();
        Long id = annotation.id();
        Gender gender = annotation.gender();
        DtoOfUserDataFromJwt dtoOfUserDataFromJwt = DtoOfUserDataFromJwt.builder()
                .id(id)
                .gender(gender)
                .nickname(nickname)
                .build();
        UserContext userContext = new UserContext(dtoOfUserDataFromJwt);
        DtoOfJwtPostAuthenticationToken dtoOfJwtPostAuthenticationToken =
                new DtoOfJwtPostAuthenticationToken(userContext);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(dtoOfJwtPostAuthenticationToken);
        return securityContext;
    }
}
