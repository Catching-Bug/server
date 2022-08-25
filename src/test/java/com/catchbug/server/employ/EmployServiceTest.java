package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
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
    
}
