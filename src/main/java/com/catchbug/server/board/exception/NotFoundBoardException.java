package com.catchbug.server.board.exception;

/**
 * <h1>NotFoundBoardException</h1>
 * <p>
 *     Exception of Not Found Board
 * </p>
 * <p>
 *     게시글을 찾을 수 없을 때 생기는 예외
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @author younghoCha
 */
public class NotFoundBoardException extends RuntimeException{

    public NotFoundBoardException(String msg){
        super(msg);
    }
}
