package com.catchbug.server.employ;


import com.catchbug.server.board.Board;
import com.catchbug.server.member.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.catchbug.server.board.BoardServiceTest.setUpBoard;
import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;

public class EmployEntityTest {

    @DisplayName("Employ는 정상적으로 생성되어야 한다.")
    @Test
    public void check_create_Employ() throws Exception{

        //given
        Board boardEntity =setUpBoard();
        Member memberEntity = setUpMember();

        LocalDateTime sampleTime = LocalDateTime.now().plusMinutes(10L);
        //when
        Employ employ = Employ.builder()
                .employee(memberEntity)
                .employer(memberEntity)
                .board(boardEntity)
                .id(1L)
                .expiryTime(sampleTime)
                .build();

        //then
        Assertions.assertEquals(1L, employ.getId());
        Assertions.assertEquals(memberEntity, employ.getEmployer());
        Assertions.assertEquals(memberEntity, employ.getEmployee());
        Assertions.assertEquals(boardEntity, employ.getBoard());
        Assertions.assertEquals(sampleTime, employ.getExpiryTime());


    }

    public static Employ setUpEmploy(Member memberEntity, Board boardEntity){
        return Employ.builder()
                .employee(memberEntity)
                .employer(memberEntity)
                .board(boardEntity)
                .id(1L)
                .expiryTime(LocalDateTime.now().plusMinutes(10))
                .build();
    }


}
