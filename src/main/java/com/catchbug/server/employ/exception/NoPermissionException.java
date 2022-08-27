package com.catchbug.server.employ.exception;

public class NoPermissionException extends RuntimeException{

    public NoPermissionException(String msg){
        super(msg);
    }
}
