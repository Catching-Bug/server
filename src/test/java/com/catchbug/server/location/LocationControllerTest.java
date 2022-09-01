package com.catchbug.server.location;

import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.util.JwtProvider;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.location.dto.DtoOfCreateLocation;
import com.catchbug.server.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LocationController.class)
public class LocationControllerTest {

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private LocationService locationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Locations 조회가 정상적으로 수행되어야 한다.")
    @Test
    public void get_Locations_Test() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        //when
        //then

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/locations")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }
    @DisplayName("Location 이 정상적으로 등록되어야한다.")
    @Test
    public void create_Location_OnSuccess() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());
        DtoOfCreateLocation dtoOfCreateLocation = DtoOfCreateLocation.builder()
                .latitude(123.123)
                .longitude(321.321)
                .detailLocation("detail")
                .region("region")
                .city("city")
                .town("town")
                .build();

        String requestBody = objectMapper.writeValueAsString(dtoOfCreateLocation);
        //when

        //then
        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/location")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @DisplayName("Location 1건 조회가 정상적으로 수행되어야 한다.")
    @Test
    public void get_Location_Test() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        //when
        //then

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/location/{id}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @DisplayName("Location 이 정상적으로 삭제되어야 한다.")
    @Test
    public void delete_location_test() throws Exception{

        //given
        Member member = setUpMember();
        String accessToken = setUpToken(member, 100000L, JwtProviderTest.TokenType.ACCESS);

        given(jwtProvider.getUserData(anyString())).willReturn(DtoOfUserDataFromJwt.builder()
                .id(1L)
                .nickname(member.getNickname())
                .gender(member.getGender())
                .build());

        //when
        //then

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/location/{id}", 1)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }


    



}
