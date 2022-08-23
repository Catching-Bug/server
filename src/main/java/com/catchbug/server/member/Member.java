package com.catchbug.server.member;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.board.exception.NotVolunteerException;
import com.catchbug.server.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Member</h1>
 * <p>
 *     Entity of Member
 * </p>
 * <p>
 *     사용자에 대한 엔티티
 * </p>
 *
 * @see com.catchbug.server.member.MemberRepository
 * @author younghoCha
 */
@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    /**
     * 사용자 id(pk)
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * oauth2 에서 제공받은 사용자 id
     */
    @Column(name = "KAKAO_ID")
    private Long kakaoId;

    /**
     * 사용자 성별
     */
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * 사용자 닉네임
     */
    @Column(name = "NICKNAME")
    private String nickname;

    /**
     * 사용자가 생성한 글
     */
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Board> hostingBoards;

    /**
     * 사용자가 배치받은 글
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOARD_ID")
    private Board hiredBoard;

    /**
     * 사용자가 등록한 위치
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Location> locations;



    /**
     * <h3> getLatestBoard : 사용자의 글 중에서 가장 최근의 글을 return 하는 메서드 </h3>
     * @return LocalDateTime 사용자가 쓴 글 중에서 가장 최근의 글을 가지고 온다.사용자가 글을 쓴 적이 없는 경우 null 을 return
     * @author : youngho cha
     */
    public LocalDateTime getLatestBoard(){

        if(this.hostingBoards == null){
            return null;
        }

        if(this.hostingBoards.isEmpty()){
            return null;
        }
        LocalDateTime maxDate = this.hostingBoards.stream().map(u -> u.getCreatedTime()).max(LocalDateTime::compareTo).get();

        return maxDate;
    }

    /**
     * 배치 요청을 메서드
     * @param board : 배치되려는 게시 글
     */
    public void volunteer(Board board){
        checkAlreadyHired(board);

        if(this.hiredBoard == null){
            this.hiredBoard = board;
            return;
        }

        checkValidBoard();

        this.hiredBoard = board;

    }

    /**
     * 배치하려는 게시 글이 이미 다른 사람에게 배치되었는지 확인하는 메서드
     * @param board : 배치 하려는 게시 글
     */
    public void checkAlreadyHired(Board board){
        if(board.checkAlreadyHired()){
            throw new AlreadyHiredException("해당 글은 이미 배치되었습니다.");
        }
    }

    /**
     * 지난 배치 게시 글이 10분이 지났는지 확인하는 메서드
     */
    public void checkValidBoard(){
        if(!this.hiredBoard.checkValidBoard()){
            throw new NotVolunteerException("이미 배치된 글이 존재합니다.");
        }
    }


}
