package com.catchbug.server.employ;

import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmployer;
import com.catchbug.server.employ.dto.DtoOfTest;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmployController {

    private final EmployService employService;

    /**
     * Employ 요청 메서드
     * @param authUser : 요청한 사용자 Authentication 객체
     * @param boardId : 요청하려는 board id(pk)
     * @return : 요청 성공 후 서버 응답
     */
    @PostMapping("/api/employ/{boardId}")
    public ResponseEntity apply(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId){

        DtoOfApplyEmploy dtoOfApplyEmploy = employService.apply(Long.parseLong(authUser.getId()), boardId);

        return new ResponseEntity(dtoOfApplyEmploy, HttpStatus.OK);
    }

    @DeleteMapping("/api/employ/{boardId}")
    public ResponseEntity cancelEmployByEmployer(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId){
        DtoOfCancelByEmployer dtoOfCancelByEmployer =
                employService.cancelEmployByEmployer(Long.parseLong(authUser.getId()), boardId);

        return new ResponseEntity(dtoOfCancelByEmployer, HttpStatus.OK);
    }

}
