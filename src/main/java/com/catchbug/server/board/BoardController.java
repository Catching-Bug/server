package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import com.catchbug.server.jwt.dto.AuthUser;
import com.catchbug.server.jwt.model.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RequiredArgsConstructor
@RestController
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity createBoard(@AuthenticationPrincipal AuthUser authUser, DtoOfCreateBoard dtoOfCreateBoard){

        DtoOfCreatedBoard dtoOfCreatedBoard =
                boardService.createBoard(Long.parseLong(authUser.getId()), dtoOfCreateBoard);

        return new ResponseEntity(dtoOfCreatedBoard, HttpStatus.CREATED);
    }
}
