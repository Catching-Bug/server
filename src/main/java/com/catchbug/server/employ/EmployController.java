package com.catchbug.server.employ;

import com.catchbug.server.common.response.Response;
import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfCancelByEmployer;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>EmployController</h1>
 * <p>
 *     Employ Controller from mvc pattern
 * </p>
 * <p>
 *     mvc 패턴의 고용정보 컨트롤러
 * </p>
 *
 * @see com.catchbug.server.employ.EmployController
 * @see com.catchbug.server.employ.Employ
 * @see com.catchbug.server.employ.EmployService
 * @author younghoCha
 */
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

    /**
     * 고용자가 고용정보를 취소하는 메서드
     * @param authUser : 요청자 Authentication 정보
     * @param boardId : 요청 대상 게시 글
     * @return 서버 응답 Dto
     */
    @DeleteMapping("/api/employ/{boardId}")
    public ResponseEntity cancelEmployByEmployer(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId){
        DtoOfCancelByEmployer dtoOfCancelByEmployer =
                employService.cancelEmployByEmployer(Long.parseLong(authUser.getId()), boardId);
        Response response = Response.builder()
                .content(dtoOfCancelByEmployer)
                .message("성공적으로 취소되었습니다.")
                .build();
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
