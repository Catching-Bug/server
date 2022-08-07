package com.catchbug.server.board.exception;

public class NotCreateException extends RuntimeException{

    public NotCreateException(){
        super();
    }

    public NotCreateException(String message){
        super(message);
    }
}
