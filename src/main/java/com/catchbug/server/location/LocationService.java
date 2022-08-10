package com.catchbug.server.location;

import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.location.dto.DtoOfCreatedLocation;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * <h1>LocationService</h1>
 * <p>
 *     Business Logic class of Location
 * </p>
 * <p>
 *     비지니스 로직이 포함되어있는 클래스
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @see com.catchbug.server.location.LocationRepository
 * @author younghoCha
 */
@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final MemberService memberService;

    /**
     * 위치 정보에 대한 엔티티 저장 메소드
     * @param user : 인증통과한 Authentication 객체
     * @param dtoOfCreateLocation : 사용자가 위치 등록하기 위해서 보낸 요청
     * @return : 등록된 엔티티
     */
    public DtoOfCreatedLocation saveLocation(AuthUser user, DtoOfCreateLocation dtoOfCreateLocation){
        Member memberEntity = memberService.getMember(Long.parseLong(user.getId()));

        Location location = Location.builder()
                .locationName(dtoOfCreateLocation.getLocationName())
                .detailLocation(dtoOfCreateLocation.getDetailLocation())
                .city(dtoOfCreateLocation.getCity())
                .latitude(dtoOfCreateLocation.getLatitude())
                .longitude(dtoOfCreateLocation.getLongitude())
                .region(dtoOfCreateLocation.getRegion())
                .town(dtoOfCreateLocation.getTown())
                .build();

        Location locationEntity = locationRepository.save(location);

        locationEntity.updateMember(memberEntity);

        return DtoOfCreatedLocation.builder()
                .locationName(dtoOfCreateLocation.getLocationName())
                .detailLocation(locationEntity.getDetailLocation())
                .city(locationEntity.getCity())
                .latitude(locationEntity.getLatitude())
                .longitude(locationEntity.getLongitude())
                .region(locationEntity.getRegion())
                .town(locationEntity.getTown())
                .build();
    }


}
