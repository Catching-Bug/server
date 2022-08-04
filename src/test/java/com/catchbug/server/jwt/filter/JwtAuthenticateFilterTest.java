package com.catchbug.server.jwt.filter;

import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.matcher.FilterSkipMatcher;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;

@SpringBootTest(classes = JwtAuthenticateFilter.class)
public class JwtAuthenticateFilterTest {

    @MockBean
    private FilterSkipMatcher filterSkipMatcher;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthenticateFilter jwtAuthenticateFilter;

    @DisplayName("Jwt Filter에서 정상적인 Authorization 객체가 리턴되어야 한다.")
    @Test
    public void get_Authorization_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String accessToken = setUpToken(member, validTime, JwtProviderTest.TokenType.ACCESS);
        HttpServletRequest request = (HttpServletRequest) HttpRequest.newBuilder().GET().header("Authorization", "Bearer " + accessToken);
        HttpServletResponse response = null;

        //when
        DtoOfJwtPostAuthenticationToken authentication = (DtoOfJwtPostAuthenticationToken) jwtAuthenticateFilter.attemptAuthentication(request, response);

        //then
        Assertions.assertEquals(authentication.getUserContext().getUsername(), "테스트아이디");

    }

}
