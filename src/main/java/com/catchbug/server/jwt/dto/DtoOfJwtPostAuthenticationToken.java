package com.catchbug.server.jwt.dto;

import com.catchbug.server.jwt.model.UserContext;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class DtoOfJwtPostAuthenticationToken extends AbstractAuthenticationToken {
    private final UserContext userContext;
    public DtoOfJwtPostAuthenticationToken(UserContext userContext) {
        super(List.of(new SimpleGrantedAuthority(userContext.getUserPayloads().getNickname())));
        this.userContext = userContext;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userContext;
    }
}
