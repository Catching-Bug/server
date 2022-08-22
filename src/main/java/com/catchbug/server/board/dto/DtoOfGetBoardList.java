package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DtoOfGetBoardList {
    private String title;
    private String content;
    private String creatorNickname;
    private LocalDateTime createdTime;
}
