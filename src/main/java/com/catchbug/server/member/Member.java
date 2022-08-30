package com.catchbug.server.member;

import com.catchbug.server.board.Board;
import com.catchbug.server.board.exception.AlreadyHiredException;
import com.catchbug.server.comment.Comment;
import com.catchbug.server.employ.Employ;
import com.catchbug.server.location.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> hostingBoards;

    /**
     * 댓글 리스트
     */
    @OneToMany(mappedBy = "commenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    /**
     * 사용자가 등록한 위치
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations;

    /**
     * 사용자 배치 정보
     */
    @OneToOne(mappedBy = "employer", fetch = FetchType.LAZY)
    private Employ employer;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private Employ employee;


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

    public void checkAbleToEmploy(){
        if(this.employer == null && this.employee == null){
            return;
        }

        checkValidEmployment();
    }
    public void checkValidEmployment(){
        //fixme 하드코딩 yml 로 빼야함
        if(this.employer.getExpiryTime().isBefore(LocalDateTime.now().minusMinutes(10))
                && this.employee.getExpiryTime().isBefore(LocalDateTime.now().minusMinutes(10))){
            return;
        }

        throw new AlreadyHiredException("이미 배치된 글이 존재하여 매칭할 수 없습니다.");
    }

}
