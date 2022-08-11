package com.catchbug.server.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfGetLocation {

    /**
     * 위치정보 이름
     */
    private String locationName;

    /**
     * 위도
     */
    private double latitude;

    /**
     * 경도
     */
    private double longitude;

    /**
     * 도, 시
     */
    private String region;

    /**
     * 시, 군, 구
     */
    private String city;

    /**
     * 읍, 면, 동
     */
    private String town;

    /**
     * 상세주소
     */
    private String detailLocation;
}
