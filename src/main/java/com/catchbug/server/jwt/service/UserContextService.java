package com.catchbug.server.jwt.service;

import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h1>UserContextService</h1>
 * <p>
 *     UserDetailsService
 * </p>
 * <p>
 *     Authentication 객체를 Controller에서 사용하기 위한 서비스
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtController
 * @author younghoCha
 */
@Service
@RequiredArgsConstructor
public class UserContextService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser authUser = AuthUser.builder().id(username).build();

        return authUser;
    }


}
