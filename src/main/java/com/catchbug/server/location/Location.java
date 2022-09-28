package com.catchbug.server.location;

import com.catchbug.server.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/**
 * <h1>Location</h1>
 * <p>
 *     Entity of Location
 * </p>
 * <p>
 *     글, 사용자에서 사용되는 Location 엔티티 클래스
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @see com.catchbug.server.location.LocationRepository
 * @author younghoCha
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    /**
     * 지역 pk id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 위치 이름
     */
    @Column
    private String locationName;

    /**
     * 위도
     */
    @Column(name = "LATITUDE")
    private double latitude;

    /**
     * 경도
     */
    @Column(name = "LONGITUDE")
    private double longitude;

    /**
     * 행정구역 : 도, 시
     */
    @Column(name = "REGION")
    private String region;

    /**
     * 행정구역 : 시, 군, 구
     */
    @Column(name = "CITY")
    private String city;

    /**
     * 행정구역 : 읍, 면, 동
     */
    @Column(name = "TOWN")
    private String town;

    /**
     * 상세주소
     */
    @Column(name = "DETAIL_LOCATION")
    private String detailLocation;

    /**
     * 해당 주소를 등록한 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Member member;

    /**
     * 주소 등록 요청을 보낸 사용자를 등록하는 메소드
     * @param member : 해당 주소 등록을 요청한 사용자
     */
    public void updateMember(Member member){
        this.member = member;
    }


}
