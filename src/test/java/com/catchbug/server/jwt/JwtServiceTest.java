package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.catchbug.server.jwt.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = JwtService.class)
public class JwtServiceTest {
    @MockBean
    private JwtFactory jwtFactory;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private JwtRepository jwtRepository;

    @Autowired
    private JwtService jwtService;

    @DisplayName("AccessToken을 정상적으로 생성한다.")
    @Test
    public void create_accessToken_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        given(jwtFactory.createAccessToken(member)).willReturn(accessToken);
        given(jwtFactory.createRefreshToken(accessToken)).willReturn(refreshToken);
        given(jwtRepository.save(any())).willReturn(null);
        //when
        DtoOfJwt dtoOfJwt = jwtService.createToken(member);

        //then
        Assertions.assertEquals(accessToken, dtoOfJwt.getAccessToken());
        Assertions.assertEquals(refreshToken, dtoOfJwt.getRefreshToken());

    }

    @DisplayName("AccessToken payloads에 있는 유저의 정보를 올바르게 받아온다.")
    @Test
    public void getPayloads_from_accessToken() throws Exception{

        //given
        //when
        String accessToken = "abc";
        DtoOfUserDataFromJwt expectedData =
                DtoOfUserDataFromJwt.builder().id(1L).gender(Gender.MALE).nickname("테스트아이디").build();

        given(jwtProvider.getUserData(accessToken)).willReturn(expectedData);
        DtoOfUserDataFromJwt actualData = jwtService.getUserData(accessToken);

        //then
        Assertions.assertEquals(expectedData.getGender(), actualData.getGender());
        Assertions.assertEquals(expectedData.getId(), actualData.getId());
        Assertions.assertEquals(expectedData.getNickname(), actualData.getNickname());

    }


    @DisplayName("RefreshToken을 통해서 정상적으로 AccessToken을 Refresh 한다.")
    @Test
    public void refresh_accessToken_OnSuccess() throws Exception{

        //given

        //when

        //then

    }



}
