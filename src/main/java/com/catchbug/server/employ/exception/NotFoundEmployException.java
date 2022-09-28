package com.catchbug.server.employ.exception;


public class NotFoundEmployException extends RuntimeException{

    public NotFoundEmployException(String msg){
        super(msg);
    }
}
