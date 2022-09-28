package com.catchbug.server.jwt.model;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <h1>AuthUser</h1>
 * <p>
 *     Custom Authentication Object
 * </p>
 * <p>
 *     스프링 시큐리티에 사용될 Authentication 객체
 * </p>
 *
 * @see com.catchbug.server.jwt.filter.JwtAuthenticationFilter
 * @author younghoCha
 */
@Getter
@Builder
public class AuthUser implements UserDetails {
    private final DtoOfUserDataFromJwt userPayloads;
    private final String id;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
