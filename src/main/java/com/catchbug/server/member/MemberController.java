package com.catchbug.server.member;

import com.catchbug.server.common.response.Response;
import com.catchbug.server.member.dto.DtoOfGetMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    @GetMapping("/api/member/{id}")
    public ResponseEntity getMemberLocation(@PathVariable Long id){
        DtoOfGetMember dtoOfGetMember = memberService.getMemberInformation(id);

        Response response = Response.builder().message("정상적으로 조회되었습니다.").content(dtoOfGetMember).build();

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
