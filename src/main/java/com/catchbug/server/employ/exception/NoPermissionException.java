package com.catchbug.server.employ.exception;

/**
 * <h1>NoPermissionException</h1>
 * <p>
 *     No Permission of request
 * </p>
 * <p>
 *     게시 글에 대한 권한이 없을 때 발생하는 예외 클래스
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @author younghoCha
 */
public class NoPermissionException extends RuntimeException{

    public NoPermissionException(String msg){
        super(msg);
    }
}
