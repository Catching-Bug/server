package com.catchbug.server.location;

import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;

public class LocationEntityTest {
    
    @DisplayName("Location Entity 에 멤버가 정상적으로 등록된다.")
    @Test
    public void update_Member() throws Exception{
    
        //given
        Member member = setUpMember();
        //when
        Location testLocationEntity = Location.builder()
                .build();

        testLocationEntity.updateMember(member);
        //then

        Assertions.assertEquals(member.getNickname(), testLocationEntity.getMember().getNickname());
        Assertions.assertEquals(member.getId(), testLocationEntity.getMember().getId());
        Assertions.assertEquals(member.getGender(), testLocationEntity.getMember().getGender());
    }
    
}
