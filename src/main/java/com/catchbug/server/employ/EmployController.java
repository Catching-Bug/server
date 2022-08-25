package com.catchbug.server.employ;

import com.catchbug.server.employ.dto.DtoOfApplyEmploy;
import com.catchbug.server.employ.dto.DtoOfTest;
import com.catchbug.server.jwt.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmployController {

    private final EmployService employService;

    @PostMapping("/api/employ/{boardId}")
    public ResponseEntity apply(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long boardId){

        DtoOfApplyEmploy dtoOfApplyEmploy = employService.apply(Long.parseLong(authUser.getId()), boardId);

        return new ResponseEntity(dtoOfApplyEmploy, HttpStatus.OK);
    }

}
