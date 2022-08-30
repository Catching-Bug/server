package com.catchbug.server.comment;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.comment.dto.DtoOfCreateComment;
import com.catchbug.server.comment.dto.DtoOfCreatedComment;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = CommentService.class)
public class CommentServiceTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private BoardService boardService;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @MockBean
    private Comment mockCommentEntity;

    @DisplayName("댓글 작성이 정상적으로 되어야 한다.")
    @Test
    public void create_Comment_OnSuccess() throws Exception{

        //given & mocking
        LocalDateTime commentAt = LocalDateTime.now();
        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();
        DtoOfCreateComment dtoOfCreateComment = DtoOfCreateComment.builder()
                .content("content").build();

        given(memberService.getMember(anyLong())).willReturn(memberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(boardEntity);
        given(mockCommentEntity.getId()).willReturn(1L);
        given(mockCommentEntity.getContent()).willReturn("content");
        given(mockCommentEntity.getCommentedAt()).willReturn(commentAt);
        given(commentRepository.save(any())).willReturn(mockCommentEntity);

        //when
        DtoOfCreatedComment actualResult = commentService.createComment(1L, 1L, dtoOfCreateComment);

        //then
        Assertions.assertEquals(mockCommentEntity.getId(), actualResult.getCommentId());
        Assertions.assertEquals(mockCommentEntity.getCommentedAt(), actualResult.getCommentedAt());
        Assertions.assertEquals(mockCommentEntity.getContent(), actualResult.getContent());
        Assertions.assertEquals(memberEntity.getNickname(), actualResult.getCommenterNickname());
    }

}
