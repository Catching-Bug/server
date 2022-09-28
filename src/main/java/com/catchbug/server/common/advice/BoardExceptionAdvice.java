package com.catchbug.server.common.advice;

import com.catchbug.server.board.exception.NotCreateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <h1>BoardExceptionAdvice</h1>
 * <p>
 *     Exception Handler of Board
 * </p>
 * <p>
 *     게시판 글 예외를 관리하는 Handler 클래스
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @see com.catchbug.server.board.BoardController
 * @author younghoCha
 */
@RestControllerAdvice
public class BoardExceptionAdvice {

    /**
     * NotCreateException 핸들러 메서드
     * @return ResponseEntity : 해당 예외에 대한 응답
     */
    @ExceptionHandler(NotCreateException.class)
    public ResponseEntity<?> handleNotCreateException(){
        return new ResponseEntity("이미 활성화된 방이 존재합니다.", HttpStatus.NOT_ACCEPTABLE);
    }
}
