package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <h1>Employ</h1>
 * <p>
 *     Employ Entity
 * </p>
 * <p>
 *     고용정보 글 엔티티
 * </p>
 *
 * @see com.catchbug.server.employ.EmployRepository
 * @see com.catchbug.server.employ.EmployService
 * @author younghoCha
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employ {

    /**
     * 고용정보 id(pk)
     */
    @Id
    @Column(name = "EMPLOY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 고용자
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYER_ID")
    private Member employer;

    /**
     * 피고용자
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Member employee;

    /**
     * 게시 글
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    /**
     * 고용정보 만료시간
     */
    @Column(name = "EXPIRY_TIME")
    private LocalDateTime expiryTime;

}
