package com.catchbug.server.board;

import com.catchbug.server.board.mock_auth.WithAuthUser;
import com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler;
import com.catchbug.server.jwt.provider.JwtAuthenticationProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Gender;
import com.catchbug.server.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BoardController.class)
public class BoardControllerTest {

    @MockBean
    private JwtAuthenticationProvider provider;

    @MockBean
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @MockBean
    private BoardService boardService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("글이 정상적으로 등록되어야 한다.")
    @Test
    public void create_board() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 10000000L, JwtProviderTest.TokenType.ACCESS);
        Board board = Board.builder()
                .title("테스트방")
                .content("테스트방입니다.")
                .build();
        String content = objectMapper.writeValueAsString(board);
        //when //then

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/board")
                        .content(content)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }



    @DisplayName("글이 성공적으로 조회되어야 한다.")
    @Test
    public void get_board_OnSuccess() throws Exception{

        //given
        
        //when

        //then

    }
    



}
