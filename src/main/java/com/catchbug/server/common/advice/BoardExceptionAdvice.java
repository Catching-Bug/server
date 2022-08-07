package com.catchbug.server.common.advice;

import com.catchbug.server.board.exception.NotCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionAdvice {
    @ExceptionHandler(NotCreateException.class)
    public ResponseEntity<?> handleNotCreateException(){
        return new ResponseEntity("이미 활성화된 방이 존재합니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
