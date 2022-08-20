package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfGetRegionCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
    //DtoOfGetRegionCount.builder().count(count(b)).latitude(avg(latitude).longitude(avg(b.longitude))).regionName(b.region)
    @Query(value = "select " +
            "b.region as regionName, " +
            "count(b) as count, " +
            "avg(b.latitude) as latitude, " +
            "avg(b.longitude) as longitude " +
            "from Board b " +
            "group by b.region")
    List<DtoOfGetRegionCount> getRegionCount();
}
