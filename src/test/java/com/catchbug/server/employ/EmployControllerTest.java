package com.catchbug.server.employ;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployController.class)
public class EmployControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private EmployService employService;

    @DisplayName("Employ 가 정상적으로 등록된다.")
    @Test
    public void create_Employ_Test_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        //when

        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/employ/{boardId}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }
    @DisplayName("Employ 가 정상적으로 취소된다.")
    @Test
    public void cancel_Employ_Test_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        //when

        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/employ/{boardId}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

}
