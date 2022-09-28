package com.catchbug.server.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * <h1>DtoOfCreateBoard</h1>
 * <p>
 *     Dto Of Board Creation
 * </p>
 * <p>
 *     게시판 글을 생성하기 위한 DTO
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @see com.catchbug.server.board.BoardController
 * @author younghoCha
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoOfCreateBoard {

    /**
     * 생성될 글 제목
     */
    @NotNull(message = "제목은 필수로 입력해야합니다.")
    @Size(min = 1, max = 10, message = "제목은 최소 1글자, 최대 10글자까지 입력가능합니다.")
    private String title;

    /**
     * 생성될 글 내용
     */
    @NotNull(message = "내용은 최소 1글자 이상이어야 합니다.")
    private String content;

    /**
     * 생성될 글 위도
     */
    private double latitude;

    /**
     * 생성될 글 경도
     */
    private double longitude;

    /**
     * 생성될 글 도, 시
     */
    private String region;

    /**
     * 생성될 글 시, 군, 구
     */
    private String city;

    /**
     * 생성될 글 읍, 면, 동
     */
    private String town;

    /**
     * 생성될 글 상세 주소
     */
    private String detailLocation;

}
