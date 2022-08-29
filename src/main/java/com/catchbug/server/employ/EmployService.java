package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.BoardService;
import com.catchbug.server.board.Status;
import com.catchbug.server.board.event.MatchedEvent;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmploy;
import com.catchbug.server.employ.dto.DtoOfMatchedStatus;
import com.catchbug.server.employ.exception.NoPermissionException;
import com.catchbug.server.employ.exception.NotFoundEmployException;
import com.catchbug.server.employ.exception.TransactionException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployService {

    private final EmployRepository employRepository;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 고용정보 생성 메서드
     * @param employeeId : 요청자 id(pk)
     * @param boardId 배치하려는 게시 글 id(pk)
     * @return 고용된 글의 정보 dto
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
        try {
            employRepository.save(createdEmployEntity);
        }catch (Exception e){
            throw new TransactionException("이미 배치되었습니다.");
        }
        eventPublisher.publishEvent(MatchedEvent.builder().board(boardEntity).status(Status.MATCHED).build());

        return DtoOfApplyEmploy.builder()
                .employeeNickname(employeeEntity.getNickname())
                .employerNickname(employerEntity.getNickname())
                .boardId(boardEntity.getId())
                .build();

    }

    /**
     * 고용자가 고용을 취소하는 메서드
     * @param memberId : 요청자 id(pk)
     * @param boardId : 요청 대상 게시 글 id(pk)
     * @return : 요청 취소에 대한 응답 dto
     */
    @Transactional
    public DtoOfCancelByEmploy cancelEmployByEmployer(Long memberId, Long boardId){
        Member memberEntity = memberService.getMember(memberId);
        Board boardEntity = boardService.getBoardEntity(boardId);
        Employ employEntity = checkCancelAuthorityByEmployer(memberEntity, boardEntity);
        System.out.println("employ id = " + employEntity.getId());
        System.out.println("----------------------");
        employRepository.deleteById(employEntity.getId());
        System.out.println("----------------------");
        eventPublisher.publishEvent(MatchedEvent.builder().board(boardEntity).status(Status.WAITING).build());
        return DtoOfCancelByEmploy.builder()
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .employerId(employEntity.getEmployer().getId())
                .employerNickname(employEntity.getEmployer().getNickname())
                .employeeId(employEntity.getEmployee().getId())
                .employeeNickname(employEntity.getEmployee().getNickname())
                .build();
    }

    /**
     * 피고용자가 고용을 취소하는 메서드
     * @param memberId : 요청자 id(pk)
     * @param employId : 고용 정보 id(pk)
     * @return : 요청 취소에 대한 응답 dto
     */
    public DtoOfCancelByEmploy cancelEmployByEmploy(Long memberId, Long employId){

        Employ employEntity = getEmployEntity(employId);

        checkStatus(employEntity, memberId);
        System.out.println("----------------");
        employRepository.deleteById(employEntity.getId());
        System.out.println("----------------");
        Board boardEntity = employEntity.getBoard();

        eventPublisher.publishEvent(MatchedEvent.builder().board(boardEntity).status(Status.WAITING).build());
        return DtoOfCancelByEmploy.builder()
                .boardTitle(boardEntity.getTitle())
                .boardId(boardEntity.getId())
                .employerId(employEntity.getEmployer().getId())
                .employerNickname(employEntity.getEmployer().getNickname())
                .employeeId(employEntity.getEmployee().getId())
                .employeeNickname(employEntity.getEmployee().getNickname())
                .build();
    }

    /**
     * 요청자(고용자)와 게시 글에 대한 고용 정보가 존재하는지 확인하는 메서드
     * @param employer : 요청자 entity
     * @param boardEntity : 게시글 id
     * @return : 존재하는 고용 정보 엔티티
     */
    public Employ checkCancelAuthorityByEmployer(Member employer, Board boardEntity){
        Employ employEntity = employRepository.findByEmployerAndBoard(employer, boardEntity)
                .orElseThrow(() -> new NoPermissionException("해당 글에 대한 권한이 없습니다."));

        return employEntity;
    }

    /**
     * 요청자(피고용자)와 게시 글에 대한 고용 정보가 존재하는지 확인하는 메서드
     * @param employee : 요청자 entity
     * @param boardEntity : 게시글 id
     * @return : 존재하는 고용 정보 엔티티
     */
    public Employ checkCancelAuthorityByEmployee(Member employee, Board boardEntity){
        Employ employEntity = employRepository.findByEmployeeAndBoard(employee, boardEntity)
                .orElseThrow(() -> new NoPermissionException("해당 글에 대한 권한이 없습니다."));

        return employEntity;
    }

    public Employ getEmployEntity(Long employId){
        Employ employEntity = employRepository.findById(employId)
                .orElseThrow(() -> new NotFoundEmployException("해당 매칭 정보는 존재하지 않습니다."));

        return employEntity;
    }

    public void checkStatus(Employ employEntity, Long memberId){
        if(employEntity.getEmployee().getId() == memberId || employEntity.getEmployer().getId() == memberId){
            return;
        }
        throw new NoPermissionException("해당 글에 매칭되지 않아 취소할 수 없습니다.");
    }



}
