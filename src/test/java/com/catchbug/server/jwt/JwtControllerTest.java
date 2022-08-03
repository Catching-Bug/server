package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import com.catchbug.server.login.LoginController;
import com.catchbug.server.login.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JwtController.class)
public class JwtControllerTest {

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("정상적으로 refresh 된 토큰이 response 된다.")
    @Test
    public void response_OnRefresh() throws Exception{
        ObjectMapper om = new ObjectMapper();


        //given
        DtoOfJwt dtoOfJwt = DtoOfJwt.builder()
                .accessToken("accessToken")
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
