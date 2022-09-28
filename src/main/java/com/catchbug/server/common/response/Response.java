package com.catchbug.server.common.response;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>Response</h1>
 * <p>
 *     Response parent Object
 * </p>
 * <p>
 *     게시판 글 엔티티
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @see com.catchbug.server.board.BoardRepository
 * @author younghoCha
 */
@Getter
@Builder
public class Response<T> {

    /**
     * 요청에 대한 응답 메세지
     */
    private String message;

    /**
     * 요청에 대한 응답 데이터
     */
    private T content;

}
