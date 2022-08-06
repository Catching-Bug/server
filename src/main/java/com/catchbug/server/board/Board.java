package com.catchbug.server.board;

import com.catchbug.server.board.auditing.BoardBaseEntity;
import com.catchbug.server.board.dto.DtoOfUpdateBoard;
import com.catchbug.server.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BoardBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BOARD_TITLE")
    private String title;

    @Column(name = "BOARD_CONTENT")
    private String content;


    public Board updateBoard(DtoOfUpdateBoard dtoOfUpdateBoard){
        this.content = dtoOfUpdateBoard.getContent();
        this.title = dtoOfUpdateBoard.getTitle();

        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_ID")
    private Member host;

    @OneToOne(mappedBy = "hiredBoard")
    private Member employee;

}
