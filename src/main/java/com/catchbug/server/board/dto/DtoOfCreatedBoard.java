package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfCreatedBoard</h1>
 * <p>
 *     Dto Of Response after Board Creation
 * </p>
 * <p>
 *     게시판 글을 생성된 후 응답될 DTO
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @see com.catchbug.server.board.BoardController
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfCreatedBoard {

    /**
     * 생성된 게시글의 id(Pk)
     */
    private Long roomId;

}
