package com.catchbug.server.location;

import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.location.dto.DtoOfCreatedLocation;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = LocationService.class)
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @MockBean
    private MemberService memberService;
    
    @DisplayName("위치 등록 테스트")
    @Test
    public void create_location_OnSuccess() throws Exception{

        Member mockMember = setUpMember();

        //given
        DtoOfCreateLocation dto = DtoOfCreateLocation.builder()
                .detailLocation("장미아파트 302동 102호")
                .latitude(38.123123123)
                .longitude(38.321321321)
                .city("창원시")
                .region("경상남도")
                .town("상남동")
                .build();
        AuthUser authUser = AuthUser.builder().userPayloads(null).id("1").build();
        Location expectedLocation = Location.builder()
                .detailLocation(dto.getDetailLocation())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .city(dto.getCity())
                .region(dto.getRegion())
                .town(dto.getTown())
                .build();
        DtoOfCreatedLocation expectedOfCreatedLocation = DtoOfCreatedLocation
                .builder()
                .longitude(dto.getLongitude())
                .detailLocation(dto.getDetailLocation())
                .city(dto.getCity())
                .town(dto.getTown())
                .region(dto.getRegion())
                .latitude(dto.getLatitude())
                .build();

        //mocking
        given(memberService.getMember(anyLong())).willReturn(mockMember);
        given(locationRepository.save(any())).willReturn(expectedLocation);
        //when
        DtoOfCreatedLocation actualOfCreatedLocation = locationService.saveLocation(authUser, dto);
        
        //then
        Assertions.assertEquals(expectedOfCreatedLocation.getDetailLocation(), actualOfCreatedLocation.getDetailLocation());
        Assertions.assertEquals(expectedOfCreatedLocation.getCity(), actualOfCreatedLocation.getCity());
        Assertions.assertEquals(expectedOfCreatedLocation.getLatitude(), actualOfCreatedLocation.getLatitude());
        Assertions.assertEquals(expectedOfCreatedLocation.getTown(), actualOfCreatedLocation.getTown());
        Assertions.assertEquals(expectedOfCreatedLocation.getLongitude(), actualOfCreatedLocation.getLongitude());
        Assertions.assertEquals(expectedOfCreatedLocation.getRegion(), actualOfCreatedLocation.getRegion());
    }
    

}
