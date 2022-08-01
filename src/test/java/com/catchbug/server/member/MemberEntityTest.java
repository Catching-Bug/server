package com.catchbug.server.member;





import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class MemberEntityTest {

    @DisplayName("Member는 성별을 가지고 있다.")
    @Test
    public void getGender_OnSuccess() throws Exception{

        //given, when
        Member maleMember = Member.builder().gender(Gender.MALE).build();
        Member femaleMember = Member.builder().gender(Gender.FEMALE).build();
        Member noneMember = Member.builder().gender(Gender.NONE).build();

        //when
        Gender maleMemberGender = maleMember.getGender();
        Gender femaleMemberGender = femaleMember.getGender();
        Gender noneMemberGender = noneMember.getGender();
        //then
        Assertions.assertEquals(Gender.MALE, maleMemberGender);
        Assertions.assertEquals(Gender.FEMALE, femaleMemberGender);
        Assertions.assertEquals(Gender.NONE, noneMemberGender);

    }
    @DisplayName("Member는 Id를 가지고 있다.")
    @Test
    public void getId_OnSuccess() throws Exception{
        //given, when
        Member maleMember = Member.builder().id(1L).build();
        Member femaleMember = Member.builder().id(2L).build();

        //then
        Assertions.assertEquals(1L, maleMember.getId());
        Assertions.assertEquals(2L, femaleMember.getId());
    }

    @DisplayName("Gender는 필수 값이다.")
    @Test
    public void gender_a() throws Exception{

        //given
        Member member = Member.builder().gender(null).build();
        //when

        //then
        Assertions.assertNull(member.getGender());

    }



    @DisplayName("최초 로그인 시, 회원 정보를 저장한다.")
    @Test
    public void firstLogin_OnSuccess() throws Exception{

        //given

        //when

        //then

    }

}