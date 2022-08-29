package com.catchbug.server.board.event;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardRepository;
import com.catchbug.server.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class BoardEmployEventHandler{
    private final BoardService boardService;
    @Async
    @TransactionalEventListener
    public void updateStatus(MatchedEvent matchedEvent) throws InterruptedException {
        Thread.sleep(5000L); // 5초 thread sleep
        System.out.println("Update Board Status Event 실행");
        Board boardEntity = matchedEvent.getBoard();
        boardEntity.updateStatus(matchedEvent.getStatus());
        boardService.saveEntity(boardEntity);
    }
}
