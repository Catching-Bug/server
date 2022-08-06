package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfUpdateBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardEntityTest {

    @DisplayName("Board의 값이 정상적으로 업데이트 되어야 한다.")
    @Test
    public void check_update_board() throws Exception{
        //given
        Board board = Board.builder()
                .content("초기 내용.")
                .title("초기 제목")
                .build();
        DtoOfUpdateBoard updateBoard = DtoOfUpdateBoard.builder()
                .content("업데이트 내용")
                .title("업데이트 제목").build();
        //when
        Board updatedBoard = board.updateBoard(updateBoard);

        //then
        Assertions.assertEquals(board.getContent(), updateBoard.getContent());
        Assertions.assertEquals(board.getTitle(), updateBoard.getTitle());

    }

}
