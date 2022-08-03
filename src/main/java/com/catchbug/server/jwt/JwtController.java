package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken){
        DtoOfJwt dtoOfJwt = jwtService.refresh(refreshToken);

        return ResponseEntity.ok(dtoOfJwt);
    }
}
