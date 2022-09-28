package com.catchbug.server.location.exception;

/**
 * <h1>NotMatchException</h1>
 * <p>
 *     Not Match Member And Location
 * </p>
 * <p>
 *     해당 위치정보가 요청자의 위치정보가 아닐 때 발생하는 예외
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
public class NotMatchException extends RuntimeException{
    /**
     * 생성자
     * @param msg : 예외 발생 이유
     */
    public NotMatchException(String msg){
        super(msg);
    }
}
