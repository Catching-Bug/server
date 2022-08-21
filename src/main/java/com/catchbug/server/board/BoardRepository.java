package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfGetCityCount;
import com.catchbug.server.board.dto.DtoOfGetRegionCount;
import com.catchbug.server.board.dto.DtoOfGetTownCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    /**
     * Region 단위로 게시글의 수량을 조회하기 위한 메서드
     * @return : 조회된 Region 단위 Dto
     */
    @Query(value = "select " +
            "b.region as regionName, " +
            "count(b) as count, " +
            "avg(b.latitude) as latitude, " +
            "avg(b.longitude) as longitude " +
            "from Board b " +
            "group by b.region")
    List<DtoOfGetRegionCount> getRegionCount();

    /**
     * city 단위로 게시글의 수량을 조회하기 위한 메서드
     * @param regionName : city 들을 조회할 특정 region
     * @return 조회된 City 단위 Dto
     */
    @Query(value = "select " +
            "b.city as cityName, " +
            "count(b) as count, " +
            "avg(b.latitude) as latitude, " +
            "avg(b.longitude) as longitude " +
            "from Board b " +
            "where b.region = :regionName " +
            "group by b.city")
    List<DtoOfGetCityCount> getCityCount(@Param("regionName") String regionName);

    /**
     * town 단위로 게시글의 수량을 조회하기 위한 메서드
     * @param cityName : town 들을 조회할 특정 city
     * @return 조회된 town 단위 Dto
     */
    @Query(value = "select " +
            "b.town as townName, " +
            "count(b) as count, " +
            "avg(b.latitude) as latitude, " +
            "avg(b.longitude) as longitude " +
            "from Board b " +
            "where b.city = :cityName " +
            "group by b.town")
    List<DtoOfGetTownCount> getTownCount(@Param("cityName") String cityName);


//    @Query("select distinct b from Board b left join b.host where b.town = :town")
//    Page<Board> findAll(@Param("town") String town, Pageable pageable);

}
