package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfBoard {
    private Long id;
    private String title;
    private String content;
    private String nickName;
}
