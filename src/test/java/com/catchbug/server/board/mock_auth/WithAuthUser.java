package com.catchbug.server.board.mock_auth;

import com.catchbug.server.member.Gender;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {

    String nickname();
    long id();
    Gender gender();

}
