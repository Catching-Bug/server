package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmployer;
import com.catchbug.server.employ.exception.NoPermissionException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = EmployService.class)
public class EmployServiceTest {

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

        DtoOfCancelByEmployer expectedResult = DtoOfCancelByEmployer.builder()
                .employeeId(memberEntity.getId())
                .employeeNickname(memberEntity.getNickname())
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .build();
        given(memberService.getMember(anyLong())).willReturn(memberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(boardEntity);
        given(employRepository.findByEmployerAndBoard(any(), any())).willReturn(Optional.of(employEntity));
        //when
        DtoOfCancelByEmployer actualResult = employService.cancelEmployByEmployer(1L, 1L);
        //then
        Assertions.assertEquals(expectedResult.getEmployeeId(), actualResult.getEmployeeId());
        Assertions.assertEquals(expectedResult.getBoardTitle(), actualResult.getBoardTitle());
        Assertions.assertEquals(expectedResult.getEmployeeNickname(), actualResult.getEmployeeNickname());
        Assertions.assertEquals(expectedResult.getBoardId(), actualResult.getBoardId());


    }

    @DisplayName("고용 정보가 존재하지 않으면 NoPermissionException 가 발생한다.")
    @Test
    public void check_NoPermissionException() throws Exception{

        Member memberEntity = setUpMember();
        Board boardEntity = setUpBoard();

        //given & mocking
        given(employRepository.findByEmployerAndBoard(any(), any())).willReturn(Optional.ofNullable(null));

        //when
        //then
        Assertions.assertThrows(NoPermissionException.class, () -> {
           employService.checkCancelAuthorityByEmployer(memberEntity, boardEntity);
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

        given(employRepository.findByEmployerAndBoard(any(), any())).willReturn(Optional.of(expectedEmploy));
        //when
        Employ actualResult = employService.checkCancelAuthorityByEmployer(memberEntity, boardEntity);

        //then
        Assertions.assertEquals(expectedEmploy.getId(), actualResult.getId());

    }

    @Test
    @DisplayName("Emply 요청을 n 번 호출할 때, 충돌이 일어났을 경우 최초의 employ가 등록되어야 하고, ObjectOptimistic" +
            "LockingFailureException이 발생되어야 함")
    void getBoard() throws InterruptedException {
        //given
        Member employerEntity = setUpMember();
        //given
        given(memberService.getMember(anyLong())).willReturn(mockMemberEntity);
        given(boardService.getBoardEntity(anyLong())).willReturn(mockBoardEntity);
        given(mockBoardEntity.getHost()).willReturn(employerEntity);
        given(mockBoardEntity.getId()).willReturn(1L);
        given(mockMemberEntity.getNickname()).willReturn("employee");
        given(mockBoardEntity.getCreatedTime()).willReturn(LocalDateTime.now());

        ExecutorService service = Executors.newFixedThreadPool(10);


        //when
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            service.execute(() -> {
                try {
                    DtoOfApplyEmploy actualResult = employService.apply((long) finalI, (long) finalI);
                    System.out.println("충돌 안남!");
                } catch (ObjectOptimisticLockingFailureException lockingFailureException) {
                    System.out.println("충돌 !");
                    // 예외를 잡아 복구작업을 해주면됩니다.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(1000);
        //then


    }




    
}
