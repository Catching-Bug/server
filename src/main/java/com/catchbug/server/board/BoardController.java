package com.catchbug.server.board;

import com.catchbug.server.board.dto.*;
import com.catchbug.server.common.response.Response;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <h1>BoardController</h1>
 * <p>
 *     Board Controller from mvc pattern
 * </p>
 * <p>
 *     mvc 패턴의 컨트롤러
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @see com.catchbug.server.board.BoardRepository
 * @see com.catchbug.server.board.Board
 * @author younghoCha
 */
@RequiredArgsConstructor
@RestController
public class BoardController {

    /**
     * Board Data Access Object
     */
    @Autowired
    private final BoardService boardService;

    /**
     * create board method
     * @param authUser : JWT 인증 객체
     * @param dtoOfCreateBoard : 방 생성을 위한 요청자 DTO
     * @param bindingResult : validation 결과
     * @return ResponseEntity : 요청에 대한 응답
     */
    @PostMapping("/api/board")
    public ResponseEntity createBoard(@AuthenticationPrincipal AuthUser authUser, @Validated @RequestBody DtoOfCreateBoard dtoOfCreateBoard, BindingResult bindingResult){

        //바인딩 오류가 존재할 경우
        if(bindingResult.hasErrors()){
            return getResponseEntityFromBindingException(bindingResult);
        }

        DtoOfCreatedBoard dtoOfCreatedBoard =
                boardService.createBoard(Long.parseLong(authUser.getId()), dtoOfCreateBoard);

        Response response = Response.builder().message("성공적으로 생성되었습니다.").content(dtoOfCreateBoard).build();

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    /**
     * region 단위로 게시물 개수를 조회하기위한 메서드
     * @return 조회된 region 개수 및 기타 데이터들이 들어있는 dto
     */
    @GetMapping("/api/regions/count")
    public ResponseEntity<?> getRegionCount(){
        List<DtoOfGetRegionCount> getRegionCountList = boardService.getRegionCount();
        Response response = Response
                .builder()
                .content(getRegionCountList)
                .message("Region 정보를 정상적으로 조회하였습니다.")
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * city 단위로 게시물 개수를 조회하기위한 메서드
     * @param regionName : city 단위로 알고싶은 region 이름
     * @return 조회된 city 개수 및 기타 데이터들이 들어있는 dto
     */
    @GetMapping("/api/cities/count")
    public ResponseEntity getCityCount(@RequestParam String regionName){

        List<DtoOfGetCityCount> dtoOfGetCityCountList =
                boardService.getCityCount(regionName);

        Response response = Response.builder()
                .content(dtoOfGetCityCountList)
                .message("City 정보를 정상적으로 조회하였습니다.")
                .build();

        return new ResponseEntity(response, HttpStatus.OK);

    }

    /**
     * town 단위로 게시물 개수를 조회하기위한 메서드
     * @param cityName : town 단위로 알고싶은 city 이름
     * @return 조회된 town 개수 및 기타 데이터들이 들어있는 dto
     */
    @GetMapping("/api/towns/count")
    public ResponseEntity getTownCount(@RequestParam String cityName){
        List<DtoOfGetTownCount> dtoOfGetTownCountList =
                boardService.getTownCount(cityName);

        Response response = Response.builder()
                .content(dtoOfGetTownCountList)
                .message("Town 정보를 정상적으로 조회하였습니다.")
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/api/test")
    public ResponseEntity<?> getBoardsList(@RequestParam String townName, Pageable pageable){
        DtoOfGetTownBoards boards =
                boardService.getTownBoards(townName, pageable);

        Response response = Response.builder()
                .content(boards)
                .message(townName + "의 게시글을 성공적으로 조회하였습니다.")
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }

    /**
     * check validation
     * @param bindingResult
     * @return ResponseEntity : validation error 시 응답 메세지
     */
    public ResponseEntity<?> getResponseEntityFromBindingException(BindingResult bindingResult){
        return new ResponseEntity(Response.builder().content(null).message("잘못된 요청 형식입니다.").build(), HttpStatus.BAD_REQUEST);

    }

    /**
     * 게시 글 1개를 조회하는 메서드
     * @param boardId : 조회하려는 게시글 id
     * @return : 조회된 게시글 정보 dto
     */
    @GetMapping("/api/board/{boardId}")
    public ResponseEntity getBoard(@PathVariable Long boardId){
        DtoOfGetBoard dtoOfGetBoard = boardService.getBoard(boardId);
        Response response = Response.builder()
                .content(dtoOfGetBoard)
                .message("게시글을 정상적으로 조회하였습니다.")
                .build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
