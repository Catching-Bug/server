package com.catchbug.server.member;

import com.catchbug.server.common.response.Response;
import com.catchbug.server.member.dto.DtoOfGetMember;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자의 정보를 조회하는 메서드
     * @param id : 조회하려는 사용자의 id
     * @return : 조회된 결과 dto
     */
    @GetMapping("/api/member/{id}")
    public ResponseEntity getMemberInformation(@PathVariable Long id){
        DtoOfGetMember dtoOfGetMember = memberService.getMemberInformation(id);

        Response response = Response.builder().message("정상적으로 조회되었습니다.").content(dtoOfGetMember).build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
