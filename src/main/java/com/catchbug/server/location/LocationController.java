package com.catchbug.server.location;

import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.member.dto.DtoOfGetLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        return ResponseEntity.ok().body(locationList);
    }

}
