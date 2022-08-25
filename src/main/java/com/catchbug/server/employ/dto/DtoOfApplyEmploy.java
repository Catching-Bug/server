package com.catchbug.server.employ.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfApplyEmploy {

    private Long boardId;
    private String employerNickname;
    private String employeeNickname;

}
