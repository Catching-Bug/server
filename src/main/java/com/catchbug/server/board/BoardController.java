package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfCreateBoard;
import com.catchbug.server.board.dto.DtoOfCreatedBoard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity createBoard(@AuthenticationPrincipal Principal principal, DtoOfCreateBoard dtoOfCreateBoard){
        log.info("실행되었음~~~~");
        DtoOfCreatedBoard dtoOfCreatedBoard = boardService.createBoard();

        return ResponseEntity.ok(dtoOfCreatedBoard);
    }
}
