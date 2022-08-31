package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.common.advice.BoardExceptionAdvice;
import com.catchbug.server.common.response.Response;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BoardController.class)
public class BoardControllerTest {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private BoardService boardService;

    @Autowired
    MockMvc mockMvc;

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
        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/board")
                        .content(content)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

    }
    @DisplayName("글이 정상적으로 등록되어야 한다.")
    @Test
    public void create_board2() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 10000000L, JwtProviderTest.TokenType.ACCESS);
        Board board = Board.builder()
                .content("테스트방입니다.")
                .build();
        String content = objectMapper.writeValueAsString(board);

        //when //then
        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/board")
                        .content(content)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }



    @DisplayName("글이 성공적으로 조회되어야 한다.")
    @Test
    public void get_board_OnSuccess() throws Exception{

        //given

        //when

        //then

    }




}
