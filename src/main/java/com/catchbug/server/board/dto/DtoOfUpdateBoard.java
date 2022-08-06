package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DtoOfUpdateBoard {

    private String title;
    private String content;

}
