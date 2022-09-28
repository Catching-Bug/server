package com.catchbug.server.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DtoOfCreateLocation {


    /**
     * 생성을 위한 위도
     */
    @NotNull
    private double latitude;

    /**
     * 생성을 위한 경도
     */
    @NotNull
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
