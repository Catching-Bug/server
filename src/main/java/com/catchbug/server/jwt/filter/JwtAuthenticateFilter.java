package com.catchbug.server.jwt.filter;

import com.catchbug.server.jwt.dto.DtoOfJwtAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class JwtAuthenticateFilter extends AbstractAuthenticationProcessingFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    public JwtAuthenticateFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 사용자의 요청에 존재하는 Authorization Header Value
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        // 인증 헤더가 없는 경우 익명 사용자로 간주 (Anonymous Authentication)
        if (Objects.isNull(authorizationHeader)) {

            return new AnonymousAuthenticationToken(UUID.randomUUID().toString(),
                    "anonymous",
                    List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
        }

        // Remove Prefix (Bearer)
        String token = getToken(authorizationHeader);

        // return 할 객체 만들기
        DtoOfJwtAuthentication dtoOfJwtPreAuthentication = getAuthentication(token);
        log.info("a");
        return this.getAuthenticationManager()
                .authenticate(dtoOfJwtPreAuthentication);
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

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("b");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }
}
