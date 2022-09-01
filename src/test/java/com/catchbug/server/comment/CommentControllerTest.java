package com.catchbug.server.comment;

import com.catchbug.server.board.BoardController;
import com.catchbug.server.comment.dto.DtoOfCreateComment;
import com.catchbug.server.comment.dto.DtoOfCreatedComment;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("댓글 등록이 정상적으로 수행되어야 한다.")
    @Test
    public void create_comment_test() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());
        DtoOfCreateComment requestBody = DtoOfCreateComment.builder()
                .content("테스트 댓글")
                .build();

        DtoOfCreatedComment dtoOfCreatedComment = DtoOfCreatedComment.builder()
                .commentId(1L)
                .content(requestBody.getContent())
                .commentedAt(LocalDateTime.now())
                .commenterNickname("닉네임")
                .build();
        String content = objectMapper.writeValueAsString(requestBody);
        given(commentService.createComment(anyLong(), anyLong(), any())).willReturn(dtoOfCreatedComment);

        //when
        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comment/{boardId}", 1)
                        .content(content)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

    }

    @DisplayName("댓글이 정상적으로 조회되어야 한다.")
    @Test
    public void get_comments_OnSuccess() throws Exception{

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
                MockMvcRequestBuilders.get("/api/comments/{boardId}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }


}
