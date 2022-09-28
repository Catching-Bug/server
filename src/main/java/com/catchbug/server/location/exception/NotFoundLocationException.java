package com.catchbug.server.location.exception;

/**
 * <h1>NotFoundLocationException</h1>
 * <p>
 *    Excetion of Not Founding Location Entity
 * </p>
 * <p>
 *     위치 정보를 찾을 수 없을 때, 발생하는 예외
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
public class NotFoundLocationException extends RuntimeException{

    /**
     * 예외 생성자
     * @param msg : 예외 발생 이유
     */
    public NotFoundLocationException(String msg){
        super(msg);
    }
}
