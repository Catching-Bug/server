package com.catchbug.server.jwt;

import com.catchbug.server.jwt.dto.DtoOfJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * <h1>JwtController</h1>
 * <p>
 *     Controller of JWT
 * </p>
 * <p>
 *     MVC 패턴 중 C에 해당하는 컨트롤러 클래스
 * </p>
 *
 * @see com.catchbug.server.jwt.JwtService
 * @author younghoCha
 */
@RestController
public class JwtController {

    @Autowired
    private JwtService jwtService;

    /**
     * 엑세스 토큰을 갱신하기위한 메서드
     * @param refreshToken : 사용자에게 전달받은 리프레시 토큰
     * @return : 갱신 성공 후의 응답
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken){
        DtoOfJwt dtoOfJwt = jwtService.refresh(refreshToken);

        return ResponseEntity.ok(dtoOfJwt);
    }
}
