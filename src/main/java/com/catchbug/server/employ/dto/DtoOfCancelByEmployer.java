package com.catchbug.server.employ.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Builder
public class DtoOfCancelByEmployer {

    private String boardTitle;
    private Long boardId;
    private String employeeNickname;
    private Long employeeId;
}
