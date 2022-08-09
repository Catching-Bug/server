package com.catchbug.server.location.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfCreatedLocation</h1>
 * <p>
 *     Dto Of Created Location Entity DTO
 * </p>
 * <p>
 *     위치 엔티티를 생성한 후 응답을 위한 DTO
 * </p>
 *
 * @see com.catchbug.server.location.Location
 * @see com.catchbug.server.location.LocationController
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfCreatedLocation {

    /**
     * 저장된 위도
     */
    private double latitude;

    /**
     * 저장된 경도
     */
    private double longitude;

    /**
     * 저장된 행정구역 도, 시
     */
    private String region;

    /**
     * 저장된 시, 구, 동
     */
    private String city;

    /**
     * 저장된 읍, 면, 동
     */
    private String town;

    /**
     * 저장된 상세주소
     */
    private String detailLocation;
}
