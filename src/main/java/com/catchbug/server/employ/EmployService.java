package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmployer;
import com.catchbug.server.employ.dto.DtoOfTest;
import com.catchbug.server.employ.exception.NoPermissionException;
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

    public DtoOfCancelByEmployer cancelEmployByEmployer(Long memberId, Long boardId){
        Member memberEntity = memberService.getMember(memberId);
        Board boardEntity = boardService.getBoardEntity(boardId);
        Employ employEntity = checkCancelAuthorityByEmployer(memberEntity, boardEntity);
        employRepository.delete(employEntity);

        return DtoOfCancelByEmployer.builder()
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .employeeId(employEntity.getEmployee().getId())
                .employeeNickname(employEntity.getEmployee().getNickname())
                .build();
    }

    public Employ checkCancelAuthorityByEmployer(Member memberEntity, Board boardEntity){
        Employ employEntity = employRepository.findByEmployerAndBoard(memberEntity, boardEntity)
                .orElseThrow(() -> new NoPermissionException("해당 글에 대한 권한이 없습니다."));

        return employEntity;
    }


}
