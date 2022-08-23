package com.catchbug.server.member;





import com.catchbug.server.board.Board;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.board.exception.NotVolunteerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.time.LocalDateTime;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = Board.class)
@MockBean(JpaMetamodelMappingContext.class)
public class MemberEntityTest {

    @MockBean
    private Board mockBoardEntity;


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

    @DisplayName("Employee 가 정상적으로 등록되어야 한다.")
    @Test
    public void check_Employ() throws Exception{

        //given
        Member employee = Member.builder()
                .gender(Gender.MALE)
                .id(2L)
                .nickname("피고용인")
                .build();

        Board board = setUpBoard();
        //when
        employee.volunteer(mockBoardEntity);
        given(mockBoardEntity.checkValidBoard()).willReturn(false);
        given(mockBoardEntity.getId()).willReturn(1L);

        Board hiredBoard = employee.getHiredBoard();
        //then
        Assertions.assertEquals(hiredBoard.getId(), board.getId());


    }
    
    @DisplayName("이미 비활성화된 글에 고용등록이 되어있을 경우 hiredBoard가 업데이트 되어야 한다.")
    @Test
    public void volunteer_not_valid_board() throws Exception{

        //given
        Member employee = Member.builder()
                .gender(Gender.MALE)
                .id(2L)
                .nickname("피고용인")
                .build();

        //when
        given(mockBoardEntity.checkAlreadyHired()).willReturn(false);
        given(mockBoardEntity.checkValidBoard()).willReturn(true);
        employee.volunteer(mockBoardEntity);

        employee.volunteer(mockBoardEntity);
        given(mockBoardEntity.getId()).willReturn(1L);

        Board hiredBoard = employee.getHiredBoard();
        //then
        Assertions.assertEquals(1L, hiredBoard.getId());
        
    }

    @DisplayName("활성화된 글에 고용등록이 되어있을 경우 NotVolunteerException 발생해야 한다.")
    @Test
    public void volunteer_valid_board() throws Exception{

        //given
        Member employee = Member.builder()
                .gender(Gender.MALE)
                .id(2L)
                .nickname("피고용인")
                .build();

        //when
        given(mockBoardEntity.checkAlreadyHired()).willReturn(false);
        given(mockBoardEntity.checkValidBoard()).willReturn(true);
        employee.volunteer(mockBoardEntity);

        given((mockBoardEntity.checkValidBoard())).willReturn(false);
        Assertions.assertThrows(NotVolunteerException.class, () -> {
                    employee.volunteer(mockBoardEntity);
                }
                );


    }
    
    @DisplayName("이미 배치된 활성화 상태 글에 배치 요청을 할 경우 AlreadyHiredException 가 발생")
    @Test
    public void volunteer_already_hired() throws Exception{

        //given
        Member employee = Member.builder()
                .gender(Gender.MALE)
                .id(2L)
                .nickname("피고용인")
                .build();

        //when
        given(mockBoardEntity.checkAlreadyHired()).willReturn(true);
        given((mockBoardEntity.checkValidBoard())).willReturn(false);

        //then
        Assertions.assertThrows(AlreadyHiredException.class, () -> {
                    employee.volunteer(mockBoardEntity);
                }
        );

    }

    
    







}