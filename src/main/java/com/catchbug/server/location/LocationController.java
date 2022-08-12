package com.catchbug.server.location;

import com.catchbug.server.common.response.Response;
import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.location.dto.DtoOfCreatedLocation;
import com.catchbug.server.member.dto.DtoOfGetLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h1>LocationController</h1>
 * <p>
 *     MVC pattern of Controller
 * </p>
 * <p>
 *     MVC 패턴의 Controller
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
@RequiredArgsConstructor
@RestController
public class LocationController {
    private final LocationService locationService;

    /**
     * 요청한 회원의 사전 등록한 위치 정보들을 조회한다.
     * @param authUser : 요쳥한 유저
     * @return 요청한 유저가 사전등록한 위치 정보 객체 리스트 dto
     */
    @GetMapping("/api/locations")
    public ResponseEntity<?> getLocations(AuthUser authUser){
        List<DtoOfGetLocation> locationList = locationService.getLocations(Long.parseLong(authUser.getId()));
        Response response = Response.builder()
                .content(locationList)
                .message("성공적으로 조회되었습니다.")
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 위치 정보 삭제 메소드
     * @param authUser : 요청자 Authentication 객체
     * @param id : 삭제하려는 위치정보 id
     * @return : 삭제 성공 시 요청자에게 응답될 ResponseEntity
     */
    @DeleteMapping("/api/location/{id}")
    public ResponseEntity<?> deleteLocation(AuthUser authUser, @PathVariable Long id){
        locationService.deleteLocation(Long.parseLong(authUser.getId()), id);
        Response response = Response.builder()
                .content(null)
                .message("성공적으로 삭제되었습니다.")
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * 위치 정보 생성 메소드
     * @param authUser : 요청자 Authentication 객체
     * @param dtoOfCreateLocation : 요청자가 생성하려는 위치 정보 객체
     * @return 생성된 위치 정보 객체 dto
     */
    @PostMapping("/api/location")
    public ResponseEntity<?> createLocation(AuthUser authUser, @RequestBody DtoOfCreateLocation dtoOfCreateLocation){
        DtoOfCreatedLocation dtoOfCreatedLocation = locationService.saveLocation(authUser, dtoOfCreateLocation);

        Response response = Response.builder()
                .content(dtoOfCreatedLocation)
                .message("성공적으로 등록되었습니다.")
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
