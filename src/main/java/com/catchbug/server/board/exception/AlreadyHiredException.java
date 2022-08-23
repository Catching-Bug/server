package com.catchbug.server.board.exception;

public class AlreadyHiredException extends RuntimeException{

    public AlreadyHiredException(String msg){
        super(msg);
    }
}
