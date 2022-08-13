package com.catchbug.server.member.exception;

/**
 * <h1>NotFoundMemberException</h1>
 * <p>
 *     Exception of Can't Find Member Entity from DB
 * </p>
 * <p>
 *     DB에서 사용자의 데이터를 찾을 수 없을 때 발생하는 예외
 * </p>
 *
 * @see com.catchbug.server.member.MemberRepository
 * @author younghoCha
 */
public class NotFoundMemberException extends RuntimeException{

    /**
     * 생성자
     * @param msg : 발생 이유
     */
    public NotFoundMemberException(String msg){
        super(msg);
    }
}
