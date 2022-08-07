package com.catchbug.server.jwt.service;

import com.catchbug.server.jwt.model.AuthUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserContextService.class)
public class UserContextServiceTest {

    @Autowired
    private UserContextService userContextService;

    @DisplayName("Security Context가 올바르게 등록되어야 한다.")
    @Test
    public void check_SecurityContext() throws Exception{

        //given
        AuthUser expectedAuthUser = AuthUser.builder().id("1").build();

        //when
        AuthUser actualAuthUser = (AuthUser) userContextService.loadUserByUsername("1");

        //then
        Assertions.assertEquals(expectedAuthUser.getAuthorities(), actualAuthUser.getAuthorities());
        Assertions.assertEquals(expectedAuthUser.getId(), actualAuthUser.getId());
        Assertions.assertEquals(expectedAuthUser.getUsername(), actualAuthUser.getUsername());

    }

}
