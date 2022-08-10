//package com.catchbug.server.location;
//
//import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
//import com.catchbug.server.jwt.util.JwtProvider;
//import com.catchbug.server.jwt.util.JwtProviderTest;
//import com.catchbug.server.location.dto.DtoOfCreateLocation;
//import com.catchbug.server.member.Member;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
//import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = LocationController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//public class LocationControllerTest {
//
//    @MockBean
//    private JwtProvider jwtProvider;
//
//    @MockBean
//    private LocationService locationService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @DisplayName("사용자의 주소 등록 요청이 성공적으로 통과되어야 한다.")
//    @Test
//    public void create_Location_OnSuccess() throws Exception{
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        //given
//        Member member = setUpMember();
//        String accessToken = setUpToken(member, 1000* 60 * 60 * 60L, JwtProviderTest.TokenType.ACCESS);
//        given(jwtProvider.getUserData(accessToken)).willReturn(DtoOfUserDataFromJwt.builder()
//                .id(1L).build());
//        DtoOfCreateLocation dto = DtoOfCreateLocation.builder()
//                .town("town")
//                .detailLocation("detailLocation")
//                .city("city")
//                .region("region")
//                .longitude(123L)
//                .latitude(321L).build();
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/location")
//                .header("Authorization", "Bearer " + accessToken)
//                .content(objectMapper.writeValueAsString(dto))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//        //then
//
//    }
//
//}
