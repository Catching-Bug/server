package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfTest;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployService {

    private final EmployRepository employRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public DtoOfApplyEmploy apply(Long employeeId, Long boardId){
        Member employeeEntity = memberService.getMember(employeeId);
        Board boardEntity = boardService.getBoardEntity(boardId);

        employeeEntity.checkAbleToEmploy();
        boardEntity.checkAbleToApply();

        Member employerEntity = boardEntity.getHost();

        Employ createdEmployEntity = Employ.builder()
                .employee(employeeEntity)
                .employer(employerEntity)
                .board(boardEntity)
                .expiryTime(boardEntity.getCreatedTime().plusMinutes(10))
                .build();

        employRepository.save(createdEmployEntity);

        return DtoOfApplyEmploy.builder()
                .employeeNickname(employeeEntity.getNickname())
                .employerNickname(employerEntity.getNickname())
                .boardId(boardEntity.getId())
                .build();

    }


}
