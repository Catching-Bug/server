package com.catchbug.server.member;

import com.catchbug.server.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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




}
