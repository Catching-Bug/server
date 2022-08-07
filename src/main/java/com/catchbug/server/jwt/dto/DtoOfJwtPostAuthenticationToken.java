package com.catchbug.server.jwt.dto;

import com.catchbug.server.jwt.model.AuthUser;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * <h1>DtoOfJwtPostAuenticationToken</h1>
 * <p>
 *     Authentication when pre authenticate object
 * </p>
 * <p>
 *     시큐리티 필터 단에서 인증 전에 생성될 Authentication 객체
 * </p>
 *
 * @see com.catchbug.server.jwt.filter.JwtAuthenticationFilter
 * @author younghoCha
 */
@Getter
public class DtoOfJwtPostAuthenticationToken extends AbstractAuthenticationToken {
    private final AuthUser authUserContext;
    public DtoOfJwtPostAuthenticationToken(AuthUser authUserContext) {
        super(List.of(new SimpleGrantedAuthority(authUserContext.getUserPayloads().getNickname())));
        this.authUserContext = authUserContext;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authUserContext;
    }
}
