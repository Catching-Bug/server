package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity createBoard(@AuthenticationPrincipal AuthUser authUser, @Validated @RequestBody DtoOfCreateBoard dtoOfCreateBoard, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return getResponseEntityFromBindingException(bindingResult);
        }

        DtoOfCreatedBoard dtoOfCreatedBoard =
                boardService.createBoard(Long.parseLong(authUser.getId()), dtoOfCreateBoard);

        return new ResponseEntity(dtoOfCreatedBoard, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getResponseEntityFromBindingException(BindingResult bindingResult){
        String message = "잘못된 요청 형식입니다.";
        return ResponseEntity.badRequest().body(message);
    }
}
