package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.board.dto.DtoOfGetRegionCount;
import com.catchbug.server.common.response.Response;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * check validation
     * @param bindingResult
     * @return ResponseEntity : validation error 시 응답 메세지
     */
    public ResponseEntity<?> getResponseEntityFromBindingException(BindingResult bindingResult){
        return new ResponseEntity(Response.builder().content(null).message("잘못된 요청 형식입니다.").build(), HttpStatus.BAD_REQUEST);
    }


}
