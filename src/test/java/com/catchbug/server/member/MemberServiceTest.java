package com.catchbug.server.member;

import com.catchbug.server.oauth2.dto.DtoOfUserProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.oauth2.Oauth2UtilTest.setUpSampleProfile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;



@SpringBootTest(classes = MemberService.class)
public class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @DisplayName("최초로그인 상황에서 최초로그인인지 판단한다.")
    @Test
    public void check_first_login() throws Exception {

        //given
        when(memberRepository.existsByKakaoId(anyLong())).thenReturn(false);

        //when
        boolean isFirst = memberService.isFirstLogin(123L);

        //then
        Assertions.assertEquals(true, isFirst);


    }

    @DisplayName("최초로그인이 아닌 상황에서 최초로그인인지 판단한다.")
    @Test
    public void check_not_first_login() throws Exception{

        //given
        when(memberRepository.existsByKakaoId(anyLong())).thenReturn(true);

        //when
        boolean isFirst = memberService.isFirstLogin(123L);

        //then
        Assertions.assertEquals(false, isFirst);

    }

    @DisplayName("최초로그인 상황에서 엔티티를 생성하고 저장한다.")
    @Test
    public void save_entity_OnFirstLogin() throws Exception{

        //given
        DtoOfUserProfile sampleProfile = setUpSampleProfile(Gender.MALE);

        Member sampleMember = Member.builder().
                nickname(sampleProfile.getProperties().getNickname())
                        .gender(sampleProfile.getKakaoAccount().getGender())
                                .kakaoId(sampleProfile.getId()).build();

        //when
        when(memberRepository.save(any())).thenReturn(sampleMember);
        when(memberRepository.existsByKakaoId(anyLong())).thenReturn(false);
        Member actualMember = memberService.login(sampleProfile);



        //then
        Assertions.assertEquals(Gender.MALE, actualMember.getGender());
        Assertions.assertEquals("테스트아이디", actualMember.getNickname());
        Assertions.assertEquals(123456L, actualMember.getKakaoId());

    }

    @DisplayName("기존로그인 시 기존 정보를 가지고 온다.")
    @Test
    public void get_entity_OnNotFirstLogin() throws Exception{

        //given
        DtoOfUserProfile sampleProfile = setUpSampleProfile(Gender.MALE);

        Member sampleMember = Member.builder().
                nickname(sampleProfile.getProperties().getNickname())
                .id(1L)
                .gender(sampleProfile.getKakaoAccount().getGender())
                .kakaoId(sampleProfile.getId()).build();

        //when
        when(memberRepository.findByKakaoId(anyLong())).thenReturn(Optional.of(sampleMember));
        when(memberRepository.existsByKakaoId(anyLong())).thenReturn(true);
        Member actualMember = memberService.login(sampleProfile);

        //then
        Assertions.assertEquals(1L, actualMember.getId());
        Assertions.assertEquals(123456L, actualMember.getKakaoId());
        Assertions.assertEquals("테스트아이디", actualMember.getNickname());
        Assertions.assertEquals(Gender.MALE, actualMember.getGender());

    }

    @DisplayName("getMember 호출 시, 파라미터 id를 가지고 있는 memberEntity가 리턴되어야 한다.")
    @Test
    public void getMember_test_OnSuccess() throws Exception{

        //given
        Member expectedMember = setUpMember();

        //when & mocking
        given(memberRepository.findById(expectedMember.getId())).willReturn(Optional.of(expectedMember));

        Member actualMember = memberService.getMember(expectedMember.getId());
        //then

        Assertions.assertEquals(expectedMember.getId(), actualMember.getId());
        Assertions.assertEquals(expectedMember.getGender(), actualMember.getGender());
        Assertions.assertEquals(expectedMember.getNickname(), actualMember.getNickname());



    }




}




