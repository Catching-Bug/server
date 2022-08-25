package com.catchbug.server.employ.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfTest {

    private String employerNickname;
    private String employeeNickname;
    private Long boardId;
}
