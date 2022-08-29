package com.catchbug.server.employ.event;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.board.Status;
import com.catchbug.server.board.event.BoardEmployEventHandler;
import com.catchbug.server.board.event.MatchedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = BoardEmployEventHandler.class)
public class BoardEmployEventListenerTest {

    @MockBean
    private BoardService boardService;

    @Autowired
    private BoardEmployEventHandler boardEmployEventHandler;
    
    @DisplayName("정상적으로 로직들이 수행되어야 한다.")
    @Test
    public void check_() throws Exception{
        BoardEmployEventHandler mockHandler = Mockito.mock(BoardEmployEventHandler.class);
        //given & mocking
        Board board = setUpBoard();
        MatchedEvent matchedEvent = MatchedEvent.builder()
                .board(board)
                .status(Status.MATCHED)
                .build();

        //when
        mockHandler.updateStatus(matchedEvent);

        //then
        verify(mockHandler, atLeastOnce()).updateStatus(matchedEvent);
    }
    


}
