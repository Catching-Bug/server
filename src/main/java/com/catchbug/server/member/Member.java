package com.catchbug.server.member;

import com.catchbug.server.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "KAKAO_ID")
    private Long kakaoId;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "NICKNAME")
    private String nickname;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Board> hostingBoards;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOARD_ID")
    private Board hiredBoard;

    /**
     * <h3> getLatestBoard : 사용자의 글 중에서 가장 최근의 글을 return 하는 메서드 </h3>
     * @return LocalDateTime 사용자가 쓴 글 중에서 가장 최근의 글을 가지고 온다.사용자가 글을 쓴 적이 없는 경우 null 을 return
     * @author : youngho cha
     */
    public LocalDateTime getLatestBoard(){
        if(this.hostingBoards == null){
            return null;
        }
        LocalDateTime maxDate = this.hostingBoards.stream().map(u -> u.getCreatedTime()).max(LocalDateTime::compareTo).get();

        return maxDate;
    }


}
