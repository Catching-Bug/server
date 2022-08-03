package com.catchbug.server.jwt;


import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = JwtFactory.class)
public class JwtFactoryTest {

    @Autowired
    private JwtFactory jwtFactory;

    @DisplayName("AccessToken 생성 테스트")
    @Test
    public void create_AccessToken_OnSuccess() throws Exception{
    
        //given
        Member member = setUpMember();
        //when
        String accessToken = jwtFactory.createAccessToken(member);

        //then
        Assertions.assertNotNull(accessToken);

    }

    @DisplayName("RefreshToken 생성 테스트")
    @Test
    public void create_refreshToken_OnSuccess() throws Exception{
        //given
        Member member = setUpMember();
        String accessToken = jwtFactory.createAccessToken(member);

        //when
        String refreshToken = jwtFactory.createRefreshToken(accessToken);

        //then
        Assertions.assertNotNull(refreshToken);

    }

    public static Member setUpMember(){
        return Member.builder().nickname("테스트아이디").id(1L).kakaoId(123L).gender(Gender.MALE).build();
    }
    
}
