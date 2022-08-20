package com.catchbug.server.board.dto;


/**
 * <h1>DtoOfGetCityCount</h1>
 * <p>
 *     Dto Of get Board Count in City
 * </p>
 * <p>
 *     City 단위로 게시글의 수를 조회하기 위한 Dto Mapping interface
 * </p>
 *
 * @see com.catchbug.server.board.BoardRepository
 * @author younghoCha
 */
public interface DtoOfGetCityCount {

    /**
     * 지역 이름을 조회
     * @return 지역이름
     */
    String getCityName();

    /**
     * 해당 지역의 게시글 수를 조회
     * @return 게시글 수
     */
    int getCount();

    /**
     * 해당 지역의 모든 게시글 수의 위경도 평균
     * @return
     */
    double getLatitude();

    /**
     * 해당 지역의 모든 게시글 수의 위경도 평균
     * @return
     */
    double getLongitude();
}
