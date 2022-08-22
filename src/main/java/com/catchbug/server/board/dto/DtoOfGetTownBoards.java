package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * <h1>DtoOfGetTownBoards</h1>
 * <p>
 *     Dto Of get summary Boards in town
 * </p>
 * <p>
 *     town 내에 존재하는 게시 글의 요약버전을 응답하기위한  Dto
 * </p>
 *
 * @see com.catchbug.server.board.BoardRepository
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfGetTownBoards {

    /**
     * 실제 게시글 데이터
     */
    private List<DtoOfBoard> dtoOfBoardList;

    /**
     * 현재 페이지 사이즈
     */
    private int size;

    /**
     * 총 페이지
     */
    private int totalPages;

    /**
     * 현재 페이지
     */
    private long page;
}
