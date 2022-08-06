package com.catchbug.server.jwt;

import com.catchbug.server.jwt.exception.ExpiredJwtException;
import com.catchbug.server.jwt.exception.ModulatedJwtException;
import com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootTest(classes = JwtAuthenticationFailureHandler.class)
public class JwtAuthenticationFailureHandlerTest {


    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @DisplayName("만료된 토큰 시, 응답 값이 올바르게 응답되어야 한다.")
    @Test
    public void check_expiredJwt_response() throws Exception{

        //given
        ModulatedJwtException exception = new ModulatedJwtException("만료된 토큰입니다.");

        //when
        jwtAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);

        //then
        Assertions.assertEquals("UTF-8", httpServletResponse.getHeader(HttpHeaders.CONTENT_ENCODING));
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponse.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, httpServletResponse.getHeader(HttpHeaders.CONTENT_TYPE));

    }

    @DisplayName("잘못된 토큰 시, 응답 값이 올바르게 응답되어야 한다.")
    @Test
    public void check_modulatedJwt_response() throws Exception{

        //given
        ExpiredJwtException exception = new ExpiredJwtException("만료된 토큰입니다.");

        //when
        jwtAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);

        //then
        Assertions.assertEquals("UTF-8", httpServletResponse.getHeader(HttpHeaders.CONTENT_ENCODING));
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), httpServletResponse.getStatus());
        Assertions.assertEquals(MediaType.APPLICATION_JSON_VALUE, httpServletResponse.getHeader(HttpHeaders.CONTENT_TYPE));

    }




}
