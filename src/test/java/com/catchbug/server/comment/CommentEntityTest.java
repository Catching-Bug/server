package com.catchbug.server.comment;


import com.catchbug.server.board.Board;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;

public class CommentEntityTest {

    @DisplayName("Comment는 정상적으로 생성될 수 있다.")
    @Test
    public void create_Reply_Test() throws Exception{

        //given
        Long id = 1L;
        String content = "테스트 메세지";
        Member member = setUpMember();
        Board board = setUpBoard();

        //when
        Comment actualResult = Comment.builder()
                .id(id)
                .commenter(member)
                .board(board)
                .content(content)
                .build();

        //then
        Assertions.assertEquals(id, actualResult.getId());
        Assertions.assertEquals(content, actualResult.getContent());
        Assertions.assertEquals(member, actualResult.getCommenter());
        Assertions.assertEquals(board, actualResult.getBoard());

    }

}
