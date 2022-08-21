package com.catchbug.server.board;

import com.catchbug.server.board.auditing.BoardBaseEntity;
import com.catchbug.server.board.dto.DtoOfUpdateBoard;
import com.catchbug.server.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_ID")
    private Member host;

    /**
     * 해당 방에 배치된 사용자 id(pk)
     */
    @OneToOne(mappedBy = "hiredBoard")
    private Member employee;

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


}
