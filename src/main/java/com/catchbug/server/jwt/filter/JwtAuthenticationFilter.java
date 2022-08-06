package com.catchbug.server.jwt.filter;

import com.catchbug.server.jwt.dto.DtoOfJwtAuthentication;
import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.dto.DtoOfUserDataFromJwt;
import com.catchbug.server.jwt.model.UserContext;
import com.catchbug.server.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 사용자의 요청에 존재하는 Authorization Header Value
        String authorizationHeader = ((HttpServletRequest)request).getHeader(AUTHORIZATION_HEADER);


        if(authorizationHeader == null){
            chain.doFilter(request, response);
            return;
        }

        // Remove Prefix (Bearer)
        String token = getToken(authorizationHeader);

        DtoOfUserDataFromJwt userPayloads = jwtProvider.getUserData(token);

        UserContext context = new UserContext(userPayloads);

        DtoOfJwtPostAuthenticationToken authentication = new DtoOfJwtPostAuthenticationToken(context);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        chain.doFilter(request, response);
    }
    /**
     * Authorization 헤더 값에서 토큰만 추출하는 메서드
     * @param authorizationHeader : 사용자 요청의 "Authorization" 헤더 값
     * @return : "Authorization" 헤더 값에서 필요한 값만 추출한 String
     */
    private String getToken(String authorizationHeader){
        return authorizationHeader.substring(BEARER_PREFIX.length());
    }

    /**
     * 사용자에게서 받은 토큰을 토대로 생성한 인증 전 Authentication 객체
     * @param token : 사용자 요청에서 받은 Token
     * @return : Authentication 객체
     */
    private DtoOfJwtAuthentication getAuthentication(String token){
        return new DtoOfJwtAuthentication(token);
    }
}
