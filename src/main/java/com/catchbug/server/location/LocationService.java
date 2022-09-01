package com.catchbug.server.location;

import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.location.dto.DtoOfCreatedLocation;
import com.catchbug.server.location.dto.DtoOfDeleteLocation;
import com.catchbug.server.location.exception.NoInformationException;
import com.catchbug.server.location.exception.NotFoundLocationException;
import com.catchbug.server.location.exception.NotMatchException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import com.catchbug.server.member.dto.DtoOfGetLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

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
                .detailLocation(locationEntity.getDetailLocation())
                .city(locationEntity.getCity())
                .latitude(locationEntity.getLatitude())
                .longitude(locationEntity.getLongitude())
                .region(locationEntity.getRegion())
                .town(locationEntity.getTown())
                .build();
    }

    /**
     * 위치정보 삭제 메서드
     * @param memberId : 요청자 id
     * @param locationId : 삭제하려는 위치정보객체 id
     */
    public void deleteLocation(Long memberId, Long locationId){
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundLocationException("해당 위치 정보를 찾을 수 없습니다."));

        if(memberId == location.getMember().getId()){
            locationRepository.delete(location);
            return;
        }

        throw new NotMatchException("해당 위치정보를 삭제할 수 없습니다.");

    }

    /**
     * 사용자가 사전에 등록한 위치 정보를 조회하는 메소드
     * @param memberId : 요청자 id
     * @return : 요청자가 사전에 등록한 위치정보 객체 리스트 dto
     */
    public List<DtoOfGetLocation> getLocations(Long memberId){
        Member memberEntity = memberService.getMember(memberId);

        List<Location> locationList = memberEntity.getLocations();

        if(locationList == null){
            throw new NoInformationException("사전에 등록한 위치 정보가 없습니다.");
        }

        return locationList.stream()
                .map(v -> DtoOfGetLocation.builder()
                        .id(v.getId())
                        .detailLocation(v.getDetailLocation())
                        .locationName(v.getLocationName())
                        .town(v.getTown())
                        .region(v.getRegion())
                        .city(v.getCity())
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .build())
                .collect(Collectors.toList());
    }

    public DtoOfGetLocation getLocation(Long memberId, Long locationId){
        Member memberEntity = memberService.getMember(memberId);

        Location locationEntity = locationRepository.findById(locationId).orElseThrow(
                () -> new NotFoundLocationException("해당하는 위치정보를 찾을 수 없습니다."));

        return DtoOfGetLocation.builder()
                .id(locationEntity.getId())
                .detailLocation(locationEntity.getDetailLocation())
                .town(locationEntity.getTown())
                .region(locationEntity.getRegion())
                .city(locationEntity.getCity())
                .detailLocation(locationEntity.getDetailLocation())
                .locationName(locationEntity.getLocationName())
                .longitude(locationEntity.getLongitude())
                .latitude(locationEntity.getLatitude())
                .build();

    }



}
