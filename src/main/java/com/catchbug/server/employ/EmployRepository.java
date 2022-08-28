package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * <h1>EmployRepository</h1>
 * <p>
 *     DAo Of Employ
 * </p>
 * <p>
 *     고용정보 DAO
 * </p>
 *
 * @see com.catchbug.server.employ.Employ
 * @see com.catchbug.server.employ.EmployService
 * @author younghoCha
 */
public interface EmployRepository extends JpaRepository<Employ, Long> {

    /**
     * 고용자와 게시 글로 Employ 정보를 획득하는 메서드
     * @param employer : 요청자 엔티티
     * @param board : 요청 대상 게시 글
     * @return 조회된 고용정보
     */
    Optional<Employ> findByEmployerAndBoard(Member employer, Board board);


}
