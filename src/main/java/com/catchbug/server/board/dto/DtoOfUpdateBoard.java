package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfUpdateBoard</h1>
 * <p>
 *     Dto Of Request Board Update
 * </p>
 * <p>
 *     사용자가 수정할 게시 글 DTO
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @see com.catchbug.server.board.BoardController
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfUpdateBoard {

    /**
     * 수정할 글 제목
     */
    private String title;

    /**
     * 수정할 글 내용
     */
    private String content;

}
