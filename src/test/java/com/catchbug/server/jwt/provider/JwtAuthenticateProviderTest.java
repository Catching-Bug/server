package com.catchbug.server.jwt.provider;

import com.catchbug.server.jwt.dto.DtoOfJwtPostAuthenticationToken;
import com.catchbug.server.jwt.filter.JwtAuthenticateFilter;
import com.catchbug.server.jwt.util.JwtProviderTest;
import com.catchbug.server.member.Member;
import io.netty.handler.codec.http.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;

import static com.catchbug.server.jwt.util.JwtFactoryTest.setUpMember;
import static com.catchbug.server.jwt.util.JwtProviderTest.setUpToken;

@SpringBootTest(classes = JwtAuthenticationProvider.class)
public class JwtAuthenticateProviderTest {



}
