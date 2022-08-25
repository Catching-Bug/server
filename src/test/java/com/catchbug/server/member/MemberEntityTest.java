package com.catchbug.server.member;


import com.catchbug.server.board.Board;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.employ.Employ;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.time.LocalDateTime;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Board.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MemberEntityTest {

    @MockBean
    private Board mockBoardEntity;

    @MockBean
    private Employ mockEmployEntity;


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

    @DisplayName("필드 값 테스트")
    @Test
    public void field_test() throws Exception{

        //given
        String nickname = "abc";
        //when
        Member member = Member.builder().nickname(nickname).build();
        //then
        Assertions.assertEquals(nickname, member.getNickname());

    }

    @DisplayName("사용자가 사전에 생성한 게시판 글이 없으면 null 이 리턴되어야 한다.")
    @Test
    public void check_board_history_time() throws Exception{

        //given
        Member member = setUpMember();
        //when
        
        LocalDateTime actual = member.getLatestBoard();

        //then
        Assertions.assertNull(actual);

    }

    @DisplayName("이미 활성화된 Employ 가 존재하면 AlreadyHiredException 가 발생한다.")
    @Test
    public void check_AlreadyHiredException() throws Exception{
        Member member = Member.builder().nickname("테스트아이디").id(1L).kakaoId(123L).gender(Gender.MALE)
                .employee(mockEmployEntity)
                .employer(mockEmployEntity).build();
        //given
        given(mockEmployEntity.getExpiryTime()).willReturn(LocalDateTime.now().minusMinutes(3));

        //when
        //then
        Assertions.assertThrows(AlreadyHiredException.class, () -> {
            member.checkValidEmployment();
        });
    }

    @DisplayName("이미 비활성화된 Employ 가 존재하면 예외가 발생하지 않는다.")
    @Test
    public void check_not_valid_Employment() throws Exception{
        Member member = Member.builder().nickname("테스트아이디").id(1L).kakaoId(123L).gender(Gender.MALE)
                .employee(mockEmployEntity)
                .employer(mockEmployEntity).build();
        //given
        given(mockEmployEntity.getExpiryTime()).willReturn(LocalDateTime.now().minusMinutes(20));

        //when
        //then
        Assertions.assertDoesNotThrow(() ->{
            member.checkValidEmployment();
        });

    }

    @DisplayName("employ 가 null 상태이면 아무 예외를 던지지 않는다.")
    @Test
    public void check_employ_is_null() throws Exception{

        //given
        Member member = setUpMember();

        //when
        //then
        Assertions.assertDoesNotThrow(() ->{
            member.checkAbleToEmploy();
        });

    }

    @DisplayName("employ 가 null 상태가 아니면 checkValidEmployment 함수를 실행한다.")
    @Test
    public  void check_run_checkValidEmployment_method_OnEmployNull(){
        //given
        Member member = setUpMember();


        //when
        Assertions.assertDoesNotThrow(()->{
            member.checkAbleToEmploy();
        });
        //then

    }
    @DisplayName("employ 가 null 상태가 아니면 checkValidEmployment 함수를 실행한다.")
    @Test
    public  void check_run_checkValidEmployment_method_OnEmployNotNull(){
        //given
        Member member = Member.builder().nickname("테스트아이디").id(1L).kakaoId(123L).gender(Gender.MALE)
                .employee(mockEmployEntity)
                .employer(mockEmployEntity).build();

        given(mockEmployEntity.getExpiryTime()).willReturn(LocalDateTime.now().minusMinutes(15));
        //when
        Assertions.assertDoesNotThrow(()->{
            member.checkAbleToEmploy();
        });
        //then

    }





    
    







}