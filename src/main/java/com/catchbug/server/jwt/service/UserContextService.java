package com.catchbug.server.jwt.service;

import com.catchbug.server.jwt.dto.AuthUser;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser authUser = AuthUser.builder().id(username).build();

        return authUser;
    }


}
