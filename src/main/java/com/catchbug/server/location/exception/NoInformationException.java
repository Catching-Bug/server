package com.catchbug.server.location.exception;

/**
 * <h1>NoInformationException</h1>
 * <p>
 *    No Content of preregistration
 * </p>
 * <p>
 *     사전에 등록한 위치 정보가 없을 떄, 발생하는 예외
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
public class NoInformationException extends RuntimeException{

    /**
     * 생성자
     * @param msg : 예외 발생 이유
     */
    public NoInformationException(String msg){
        super("사전에 등록한 위치정보가 없습니다.");
    }
}
