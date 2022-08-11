package com.catchbug.server.location;

import com.catchbug.server.jwt.model.AuthUser;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.location.dto.DtoOfCreatedLocation;
import com.catchbug.server.location.dto.DtoOfDeleteLocation;
import com.catchbug.server.location.exception.NotFoundLocationException;
import com.catchbug.server.location.exception.NotMatchException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
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
                .detailLocation("detailLocation")
                .latitude(123123.123123)
                .longitude(321321.321321)
                .city("city")
                .region("region")
                .town("town")
                .build();
        AuthUser authUser = AuthUser.builder().userPayloads(null).id("1").build();
        Location expectedLocation = setUpLocation();
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
        Assertions.assertEquals(expectedOfCreatedLocation.getDetailLocation(),
                actualOfCreatedLocation.getDetailLocation());
        Assertions.assertEquals(expectedOfCreatedLocation.getCity(), actualOfCreatedLocation.getCity());
        Assertions.assertEquals(expectedOfCreatedLocation.getLatitude(), actualOfCreatedLocation.getLatitude());
        Assertions.assertEquals(expectedOfCreatedLocation.getTown(), actualOfCreatedLocation.getTown());
        Assertions.assertEquals(expectedOfCreatedLocation.getLongitude(), actualOfCreatedLocation.getLongitude());
        Assertions.assertEquals(expectedOfCreatedLocation.getRegion(), actualOfCreatedLocation.getRegion());
    }

    @DisplayName("Location 삭제 테스트 - 사전에 회원이 등록한 위치 정보가 삭제되어야 한다.")
    @Test
    public void delete_location_test() throws Exception{

        //given & mocking
        DtoOfDeleteLocation dto = DtoOfDeleteLocation.builder().id(1L).build();
        given(locationRepository.findById(anyLong())).willReturn(Optional.ofNullable(setUpLocation()));

        //then
        Assertions.assertDoesNotThrow(()->{
            locationService.deleteLocation(1L, dto);
        });
    }

    @DisplayName("Location 삭제 테스트 - 해당 Location 이 요청한 Member 의 위치 정보가 아닌경우 notMatchException 예외가 발생한다.")
    @Test
    public void delete_location_not_match_member() throws Exception{


        //given & mocking
        DtoOfDeleteLocation dto = DtoOfDeleteLocation.builder().id(1L).build();
        given(locationRepository.findById(anyLong())).willReturn(Optional.ofNullable(setUpLocation()));

        //when

        //then

        Assertions.assertThrows(NotMatchException.class, () -> {
            locationService.deleteLocation(2L, dto);
        });

    }

    @DisplayName("위치 정보를 찾을 수 없을 때 NotFoundLocationException 이 발생한다.")
    @Test
    public void check_NotFoundLocationException(){

        //given

        //when
        given(locationRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //then
        Assertions.assertThrows(NotFoundLocationException.class, () -> {
            locationService.deleteLocation(1L, DtoOfDeleteLocation.builder().id(1L).build());
        });
    }

    

    public static Location setUpLocation(){
        return Location.builder()
                .id(1L)
                .locationName("locationName")
                .town("town")
                .region("region")
                .city("city")
                .detailLocation("detailLocation")
                .latitude(123123.123123)
                .longitude(321321.321321)
                .member(setUpMember())
                .locationName("locationName")
                .build();
    }
    

}
