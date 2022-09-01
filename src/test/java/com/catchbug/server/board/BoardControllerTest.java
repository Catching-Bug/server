package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.board.dto.DtoOfGetBoard;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @DisplayName("글 제목이 존재하지 않을 때, 올바른 응답이 되어야 한다.")
    @Test
    public void create_board_OnBindingException() throws Exception{

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



    @DisplayName("글 1개가 성공적으로 조회되어야 한다.")
    @Test
    public void get_board_OnSuccess() throws Exception{

        //given & mocking
        Member member = setUpMember();
        String accessToken = setUpToken(member, 10000000L, JwtProviderTest.TokenType.ACCESS);
        Board boardEntity = setUpBoard();
        DtoOfGetBoard expectedResult = DtoOfGetBoard.builder()
                .id(boardEntity.getId())
                .region(boardEntity.getRegion())
                .town(boardEntity.getTown())
                .city(boardEntity.getCity())
                .detailLocation(boardEntity.getDetailLocation())
                .roomContent(boardEntity.getContent())
                .roomTitle(boardEntity.getTitle())
                .longitude(boardEntity.getLongitude())
                .latitude(boardEntity.getLatitude())
                .build();
        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());
        given(boardService.getBoard(anyLong())).willReturn(expectedResult);

        //when
        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/board/{boardId}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());


    }

    @DisplayName("region 단위로 정상적으로 게시글 개수가 조회되어야 한다.")
    @Test
    public void get_board_count_OnRegion() throws Exception{

        //given & mocking
        Member member = setUpMember();
        String accessToken = setUpToken(member, 10000000L, JwtProviderTest.TokenType.ACCESS);
        Board boardEntity = setUpBoard();
        DtoOfGetBoard expectedResult = DtoOfGetBoard.builder()
                .id(boardEntity.getId())
                .region(boardEntity.getRegion())
                .town(boardEntity.getTown())
                .city(boardEntity.getCity())
                .detailLocation(boardEntity.getDetailLocation())
                .roomContent(boardEntity.getContent())
                .roomTitle(boardEntity.getTitle())
                .longitude(boardEntity.getLongitude())
                .latitude(boardEntity.getLatitude())
                .build();
        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());
        given(boardService.getBoard(anyLong())).willReturn(expectedResult);

        //when
        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/regions/count")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());


    }





}
