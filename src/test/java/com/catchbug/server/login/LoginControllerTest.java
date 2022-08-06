package com.catchbug.server.login;

import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.login.dto.DtoOfLoginSuccess;
import com.catchbug.server.member.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(controllers = LoginController.class)
public class LoginControllerTest {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private LoginService loginService;

    @Autowired
    private MockMvc mockMvc;



    @DisplayName("클라이언트로부터 로그인 요청을 정상적으로 받는다.")
    @Test
    public void login_controller_onSuccess() throws Exception{

        DtoOfLoginSuccess dtoOfLoginSuccess = DtoOfLoginSuccess.builder().
                        accessToken("accessToken").
                        refreshToken("refreshToken").
                        gender(Gender.MALE)
                        .nickname("tom").build();

        //given
        given(loginService.login(any())).willReturn(null);

        // when & then
        this.mockMvc.perform(

                MockMvcRequestBuilders.get("/login/oauth?code=abcdefg")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
//                .andExpect(jsonPath("accessToken").value("accessToken"))
//                .andExpect(jsonPath("refreshToken").value("refreshToken"))
//                .andExpect(jsonPath("nickName").value("tom"))
//                .andExpect(jsonPath("gender").value("MALE"));

    }



    @DisplayName("카카오 Resource 서버로부터 유저의 정보를 받는데, 성공한다.")
    @Test
    public void receive_Information_from_oauth_server_OnSuccess() throws Exception{

        //given

        //when

        //then

    }

}