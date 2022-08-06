package com.catchbug.server.config;

import com.catchbug.server.jwt.filter.JwtAuthenticateFilter;
import com.catchbug.server.jwt.handler.JwtAuthenticationFailureHandler;
import com.catchbug.server.jwt.matcher.FilterSkipMatcher;
import com.catchbug.server.jwt.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    // jwt 인증 필터
    public Filter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher filterSkipMatcher = new FilterSkipMatcher(
                List.of("/api/refresh", "/api/logout", "/token/refresh"),
                List.of("/api/dd/**")
        );
        //필터 생성
        JwtAuthenticateFilter jwtAuthenticateFilter = new JwtAuthenticateFilter(filterSkipMatcher);

        //필터 인증 매니저 설정
        jwtAuthenticateFilter.setAuthenticationManager(super.authenticationManager());

        //실패 Handler 설정
        jwtAuthenticateFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
        return jwtAuthenticateFilter;
    }
    // authentication manager setting
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // login disable
        http.formLogin().disable();

        // csrf disable
        http.csrf().disable();

        // http basic diable
        http.httpBasic().disable();

        // JWT
        // 필터 등록
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/token/refresh").permitAll()
                .antMatchers("/api/board").authenticated();
    }
}
