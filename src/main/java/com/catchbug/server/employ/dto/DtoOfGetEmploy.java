package com.catchbug.server.employ.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfGetEmploy {

    private Long employId;
    private Long employeeId;
    private Long employerId;
    private String employeeNickname;
    private String employerNickname;
}
