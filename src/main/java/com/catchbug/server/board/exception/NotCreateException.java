package com.catchbug.server.board.exception;

/**
 * <h1>NotCreateException</h1>
 * <p>
 *     Can't Create Board Exception
 * </p>
 * <p>
 *     게시글을 쓸 수 없을 때, 발생하는 예외 클래스
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @author younghoCha
 */
public class NotCreateException extends RuntimeException{


    /**
     * message가 필요한 생성자
     * @param message : 예외 이유 message
     */
    public NotCreateException(String message){
        super(message);
    }
}
