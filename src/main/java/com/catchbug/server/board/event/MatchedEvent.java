package com.catchbug.server.board.event;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class MatchedEvent{

    private final Board board;
    private final Status status;

    @Builder
    public MatchedEvent(Board board, Status status){
        this.board = board;
        this.status = status;
    }
}
