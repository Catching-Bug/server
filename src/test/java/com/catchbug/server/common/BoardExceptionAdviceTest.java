package com.catchbug.server.common;

import com.catchbug.server.common.advice.BoardExceptionAdvice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = BoardExceptionAdvice.class)
public class BoardExceptionAdviceTest {

    @Autowired
    private BoardExceptionAdvice boardExceptionAdvice;

    @DisplayName("방 생성 오류 예외에 대한 응답 체크")
    @Test
    public void check_validation_from_creation_board() throws Exception{

        //given
        ResponseEntity exceptedResponse = new ResponseEntity("이미 활성화된 방이 존재합니다.", HttpStatus.NOT_ACCEPTABLE);

        //when
        ResponseEntity actualResponse = boardExceptionAdvice.handleNotCreateException();

        //then

        Assertions.assertEquals(exceptedResponse.getBody(), actualResponse.getBody());
        Assertions.assertEquals(exceptedResponse.getStatusCode(), actualResponse.getStatusCode());

    }

}
