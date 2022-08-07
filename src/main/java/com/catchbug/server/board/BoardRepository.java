package com.catchbug.server.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>BoardRepository</h1>
 * <p>
 *     DAo Of Board
 * </p>
 * <p>
 *     게시판 글 DAO
 * </p>
 *
 * @see com.catchbug.server.board.Board
 * @see com.catchbug.server.board.BoardService
 * @author younghoCha
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
}
