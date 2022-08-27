package com.catchbug.server.employ;

import com.catchbug.server.board.Board;
import com.catchbug.server.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployRepository extends JpaRepository<Employ, Long> {

    Optional<Employ> findByEmployerAndBoard(Member employer, Board board);
}
