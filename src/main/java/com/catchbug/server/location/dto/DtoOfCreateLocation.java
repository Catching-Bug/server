package com.catchbug.server.location.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfCreateLocation</h1>
 * <p>
 *     Dto Of location Creation
 * </p>
 * <p>
 *     위치 엔티티를 생성하기 위한 DTO
 * </p>
 *
 * @see com.catchbug.server.location.Location
 * @see com.catchbug.server.location.LocationController
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfCreateLocation {

    /**
     * 생성을 위한 위도
     */
    private double latitude;

    /**
     * 생성을 위한 경도
     */
    private double longitude;

    /**
     * 생성을 위한 행정구역 도, 시
     */
    private String region;

    /**
     * 생성을 위한 시, 구, 동
     */
    private String city;

    /**
     * 생성을 위한 읍, 면, 동
     */
    private String town;

    /**
     * 생성을 위한 상세주소
     */
    private String detailLocation;
}
