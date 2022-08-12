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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @MockBean
    private Member mockMember;

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
            locationService.deleteLocation(1L, 1L);
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
            locationService.deleteLocation(2L, 1L);
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
            locationService.deleteLocation(1L, 1L);
        });
    }

    @DisplayName("유저가 사전 등록한 위치 정보들을 정상적으로 조회한다.")
    @Test
    public void get_locations() throws Exception{

        //given
        List<Location> locationList = new ArrayList<>();
        locationList.add(setUpLocation());
        locationList.add(setUpLocation());
        locationList.add(setUpLocation());

        given(memberService.getMember(anyLong())).willReturn(mockMember);
        given(mockMember.getLocations()).willReturn(locationList);

        List<DtoOfGetLocation> expectedResult = locationList.stream().map(v ->
                DtoOfGetLocation.builder()
                        .locationName(v.getLocationName())
                        .city(v.getCity())
                        .town(v.getTown())
                        .detailLocation(v.getDetailLocation())
                        .latitude(v.getLatitude())
                        .longitude(v.getLongitude())
                        .region(v.getRegion())
                        .build()
                ).collect(Collectors.toList());

        //when
        List<DtoOfGetLocation> actualResult = locationService.getLocations(1L);

        //then
        Assertions.assertEquals(expectedResult.size(), actualResult.size());
        Assertions.assertEquals(expectedResult.get(0).getCity(), actualResult.get(0).getCity());
        Assertions.assertEquals(expectedResult.get(0).getDetailLocation(), actualResult.get(0).getDetailLocation());
        Assertions.assertEquals(expectedResult.get(1).getLatitude(), actualResult.get(1).getLatitude());
        Assertions.assertEquals(expectedResult.get(2).getTown(), actualResult.get(1).getTown());

    }
    @DisplayName("유저가 사전 등록한 위치 정보가 없는 경우 NoInformationException 예외가 발생한다.")
    @Test
    public void get_locations_OnNoInformationException() throws Exception{

        //given
        given(memberService.getMember(anyLong())).willReturn(mockMember);
        given(mockMember.getLocations()).willReturn(null);

        //when
        //then
        Assertions.assertThrows(NoInformationException.class, ()->
        {
            locationService.getLocations(1L);
        });

    }

    @DisplayName("위치 정보 1개를 성공적으로 조회한다.")
    @Test
    public void get_location_OnSuccess() throws Exception{

        //given
        Location location = setUpLocation();
        given(memberService.getMember(anyLong())).willReturn(mockMember);
        given(locationRepository.findById(anyLong())).willReturn(Optional.of(location));
        //when
        DtoOfGetLocation actualResult = locationService.getLocation(1L, 1L);
        //then

        Assertions.assertEquals(location.getLocationName(), actualResult.getLocationName());
        Assertions.assertEquals(location.getDetailLocation(), actualResult.getDetailLocation());
        Assertions.assertEquals(location.getTown(), actualResult.getTown());
        Assertions.assertEquals(location.getCity(), actualResult.getCity());
        Assertions.assertEquals(location.getLongitude(), actualResult.getLongitude());
        Assertions.assertEquals(location.getLatitude(), actualResult.getLatitude());
        Assertions.assertEquals(location.getId(), actualResult.getId());
        Assertions.assertEquals(location.getRegion(), actualResult.getRegion());

    }

    @DisplayName("위치 정보를 찾을 수 없을 때, NotFoundLocationException 이 발생한다.")
    @Test
    public void get_location_OnNotFoundLocationException() throws Exception{
    
        //given
        given(locationRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        given(memberService.getMember(anyLong())).willReturn(setUpMember());
        //when
        //then
        Assertions.assertThrows(NotFoundLocationException.class, () ->{
            locationService.getLocation(1L, 1L);
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
