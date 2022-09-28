package com.catchbug.server.member.dto;

import lombok.Builder;
import lombok.Getter;
/**
 * <h1>DtoOfGetLocation</h1>
 * <p>
 *     Dto Of Get Location
 * </p>
 * <p>
 *     사용자의 위치 정보를 전달해주기위한 DTO
 * </p>
 *
 * @see com.catchbug.server.member.MemberService
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfGetLocation {


    /**
     * 사용자 위치 정보 id(pk)
     */
    private Long id;
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
