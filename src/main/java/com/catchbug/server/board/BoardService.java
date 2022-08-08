package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.board.exception.NotCreateException;
import com.catchbug.server.member.Member;
import com.catchbug.server.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <h1>BoardService</h1>
 * <p>
 *     Business Object of Board
 * </p>
 * <p>
 *     게시 글에 대한 비즈니스 로직 클래스
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @see com.catchbug.server.board.BoardController
 * @see com.catchbug.server.board.BoardRepository
 * @author younghoCha
 */
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberService memberService;

    /**
     * <h3>Board valid time</h3>
     * 게시판 글의 활성 시간
     */
    private final Long BOARD_VALID_TIME = 10L;

    /**
     * 게시판 글 생성 메소드
     * @param memberId : 요청자 id
     * @param dtoOfCreateBoard : 게시판 글 생성 dto
     * @return
     */
    public DtoOfCreatedBoard createBoard(Long memberId, DtoOfCreateBoard dtoOfCreateBoard){

        Member memberEntity = memberService.getMember(memberId);

        if(!checkCreatable(memberEntity)){
            throw new NotCreateException("활성화된 글이 존재합니다. 방을 생성할 수 없습니다.");
        }

        Board board = Board.builder()
                .title(dtoOfCreateBoard.getTitle())
                .content(dtoOfCreateBoard.getContent())
                .host(memberEntity)
                .build();

        Board boardEntity = boardRepository.save(board);

        DtoOfCreatedBoard dtoOfCreatedBoard = DtoOfCreatedBoard.builder().roomId(boardEntity.getId())
                .build();

        return dtoOfCreatedBoard;
    }

    /**
     * checkCreatable : 사전에 생성한 Board 가 valid time 을 지났는지 판단.
     * @param memberEntity : Board 를 생성하려는 사용자
     * @return true : 사전에 생성한 게시판이 valid time 을 지나지 않았으면 true 리턴
     * @return false : 사전에 생성한 게시판이 valid time 을 지났으면 false 리턴
     *
     */
    public boolean checkCreatable(Member memberEntity){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime latestBoardDate = memberEntity.getLatestBoard();

        if(latestBoardDate == null){
            return true;
        }

        LocalDateTime boardValidDate = memberEntity.getLatestBoard().plusMinutes(BOARD_VALID_TIME);

        return boardValidDate.compareTo(now) > 0 ? false : true;

    }
}
