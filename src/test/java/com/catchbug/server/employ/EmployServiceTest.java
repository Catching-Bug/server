package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmploy;
import com.catchbug.server.employ.exception.NoPermissionException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = EmployService.class)
public class EmployServiceTest {

    @MockBean
    private ApplicationEventPublisher applicationEventPublisher;
    @MockBean
    private EmployRepository employRepository;

    @MockBean
    private Board mockBoardEntity;

    @MockBean
    private Member mockMemberEntity;

    @MockBean
    private MemberService memberService;

    @MockBean
    private BoardService boardService;

    @Autowired
    private EmployService employService;

    @DisplayName("정상적으로 Employ 가 생성되어야 한다.")
    @Test
    public void apply_employ_OnSuccess() throws Exception{

        Member employerEntity = setUpMember();
        //given
        given(memberService.getMember(anyLong())).willReturn(mockMemberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(mockBoardEntity);
        given(mockBoardEntity.getHost()).willReturn(employerEntity);
        given(mockBoardEntity.getId()).willReturn(1L);
        given(mockMemberEntity.getNickname()).willReturn("employee");
        given(mockBoardEntity.getCreatedTime()).willReturn(LocalDateTime.now());

        //when
        DtoOfApplyEmploy actualResult = employService.apply(1L, 1L);

        //then
        Assertions.assertEquals(employerEntity.getNickname(), actualResult.getEmployerNickname());
        Assertions.assertEquals(mockMemberEntity.getNickname(), actualResult.getEmployeeNickname());
        Assertions.assertEquals(mockBoardEntity.getId(), actualResult.getBoardId());
        
    }

    @DisplayName("글 생성자가 고용을 취소할 수 있다.")
    @Test
    public void cancel_Employ_ByEmployer() throws Exception{

        //given
        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();
        Employ employEntity = Employ.builder()
                        .employer(memberEntity)
                                .employee(memberEntity)
                .board(boardEntity)
                .id(1L)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                                        .build();

        DtoOfCancelByEmploy expectedResult = DtoOfCancelByEmploy.builder()
                .employeeId(memberEntity.getId())
                .employeeNickname(memberEntity.getNickname())
                .employerNickname(memberEntity.getNickname())
                .employerId(memberEntity.getId())
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .build();
        given(memberService.getMember(anyLong())).willReturn(memberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(boardEntity);
        given(employRepository.findById(anyLong())).willReturn(Optional.of(employEntity));
        //when
        DtoOfCancelByEmploy actualResult = employService.cancelEmploy(1L, 1L);
        //then
        Assertions.assertEquals(expectedResult.getEmployeeId(), actualResult.getEmployeeId());
        Assertions.assertEquals(expectedResult.getBoardTitle(), actualResult.getBoardTitle());
        Assertions.assertEquals(expectedResult.getEmployeeNickname(), actualResult.getEmployeeNickname());
        Assertions.assertEquals(expectedResult.getBoardId(), actualResult.getBoardId());
        Assertions.assertEquals(expectedResult.getEmployerId(), actualResult.getEmployerId());
        Assertions.assertEquals(expectedResult.getEmployerNickname(), actualResult.getEmployerNickname());


    }

    @DisplayName("피고용자가 고용을 취소할 수 있다.")
    @Test
    public void cancel_Employ_ByEmployee() throws Exception{

        //given
        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();
        Employ employEntity = Employ.builder()
                .employer(memberEntity)
                .employee(memberEntity)
                .board(boardEntity)
                .id(1L)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .build();

        DtoOfCancelByEmploy expectedResult = DtoOfCancelByEmploy.builder()
                .employeeId(memberEntity.getId())
                .employeeNickname(memberEntity.getNickname())
                .employerNickname(memberEntity.getNickname())
                .employerId(memberEntity.getId())
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .build();
        given(memberService.getMember(anyLong())).willReturn(memberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(boardEntity);
        given(employRepository.findById(anyLong())).willReturn(Optional.of(employEntity));
        //when
        DtoOfCancelByEmploy actualResult = employService.cancelEmploy(1L, 1L);
        //then
        Assertions.assertEquals(expectedResult.getEmployeeId(), actualResult.getEmployeeId());
        Assertions.assertEquals(expectedResult.getBoardTitle(), actualResult.getBoardTitle());
        Assertions.assertEquals(expectedResult.getEmployeeNickname(), actualResult.getEmployeeNickname());
        Assertions.assertEquals(expectedResult.getBoardId(), actualResult.getBoardId());
        Assertions.assertEquals(expectedResult.getEmployerId(), actualResult.getEmployerId());
        Assertions.assertEquals(expectedResult.getEmployerNickname(), actualResult.getEmployerNickname());


    }
    @DisplayName("고용 정보의 member id와 요청자 id가 같지 않으면 NoPermissionException 가 발생한다.")
    @Test
    public void check_NoPermissionException_in_employee() throws Exception{
        //given
        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();
        Employ employEntity = Employ.builder()
                .employee(memberEntity)
                        .employer(memberEntity)
                .board(boardEntity)
                .id(1L)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                                .build();

        //when
        //then
        Assertions.assertThrows(NoPermissionException.class, () -> {
            employService.checkStatus(employEntity, 2L);
        });

    }

    @DisplayName("고용 정보가 존재하면 정상적인 Employ Entity 를 반환해야한다.")
    @Test
    public void check_isExists_Employ_Entity() throws Exception{
        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();
        //given & mocking
        Employ expectedEmploy = Employ.builder()
                .id(1L)
                .employer(memberEntity)
                .employee(memberEntity)
                .board(boardEntity)
                .expiryTime(LocalDateTime.now().plusMinutes(10L))
                .build();

        given(employRepository.findById(anyLong())).willReturn(Optional.of(expectedEmploy));
        //when
        Employ actualResult = employService.getEmployEntity(1L);

        //then
        Assertions.assertEquals(expectedEmploy.getId(), actualResult.getId());

    }





    
}
