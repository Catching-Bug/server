package com.catchbug.server.board.exception;

public class ExpiredBoardException extends RuntimeException{

    public ExpiredBoardException(String msg){
        super(msg);
    }
}
