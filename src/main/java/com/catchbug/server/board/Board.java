package com.catchbug.server.board;

import com.catchbug.server.board.auditing.BoardBaseEntity;
import com.catchbug.server.board.dto.DtoOfUpdateBoard;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.board.exception.ExpiredBoardException;
import com.catchbug.server.employ.Employ;
import com.catchbug.server.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <h1>Board</h1>
 * <p>
 *     Board Entity
 * </p>
 * <p>
 *     게시판 글 엔티티
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @see com.catchbug.server.board.BoardRepository
 * @author younghoCha
 */
@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BoardBaseEntity {

    /**
     * 게시판 글 ID(primary key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시 글 제목
     */
    @Column(name = "BOARD_TITLE")
    private String title;

    /**
     * 게시 글 내용
     */
    @Column(name = "BOARD_CONTENT")
    private String content;

    /**
     * 만료 시간
     */
    @Column(name = "EXPIRY_TIME")
    private LocalDateTime expiryTime;

    /**
     * 게시 글 업데이트(수정)을 위한 메소드
     * @param dtoOfUpdateBoard : 게시 글 업데이트를 위한 DTO
     * @return : 업데이트 된 후의 this 클래스
     */
    public Board updateBoard(DtoOfUpdateBoard dtoOfUpdateBoard){
        this.content = dtoOfUpdateBoard.getContent();
        this.title = dtoOfUpdateBoard.getTitle();

        return this;
    }

    /**
     * 해당 방을 생성한 사용자 ID(pk)
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_ID")
    private Member host;

    /**
     * 해당 방의 고용정보
     */
    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employ employ;

    /**
     * 방 고용 상태
     */
    private Status status;

    /**
     * 도, 시
     */
    private String region;

    /**
     * 시군구
     */
    private String city;

    /**
     * 읍면동
     */
    private String town;

    /**
     * 상세주소
     */
    private String detailLocation;

    /**
     * 위도
     */
    private double latitude;

    /**
     * 경도
     */
    private double longitude;

    /**
     * 방 고용 상황
     */

    public void checkValidBoard(){
        //fixme 하드코딩 수정해야함
        if(this.getCreatedTime().isBefore(LocalDateTime.now().minusMinutes(10L))){
            throw new ExpiredBoardException("해당 글은 비활성화된 방입니다.");
        }

    }

    public void checkAlreadyHired(){
        if(this.employ == null){
            return;
        }
            throw new AlreadyHiredException("해당 글은 이미 배치되었습니다.");
    }

    public void checkAbleToApply(){
        checkAlreadyHired();
        checkValidBoard();
    }

    public void updateStatus(Status status){
        this.status = status;
    }

}
