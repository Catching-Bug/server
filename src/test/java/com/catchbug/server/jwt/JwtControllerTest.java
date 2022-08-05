package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.jwt.filter.JwtAuthenticateFilter;
import com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler;
import com.catchbug.server.jwt.provider.JwtAuthenticationProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(controllers = JwtController.class)
public class JwtControllerTest {

    @MockBean
    private JwtAuthenticateFilter jwtAuthenticateFilter;

    @MockBean
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @MockBean
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("정상적으로 refresh 된 토큰이 response 된다.")
    @Test
    public void response_OnRefresh() throws Exception{

        Member member = setUpMember();
        Long validTime = 30 * 60 * 1000L;
        String accessToken = setUpToken(member, validTime, JwtProviderTest.TokenType.ACCESS);

        //given
        DtoOfJwt dtoOfJwt = DtoOfJwt.builder()
                .accessToken(accessToken)
                .refreshToken("refreshToken").build();
        String body = "\"refreshToken\":\"refresh-token\"";
        given(jwtService.refresh(anyString())).willReturn(dtoOfJwt);


        //when & then
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/token/refresh")
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

}
