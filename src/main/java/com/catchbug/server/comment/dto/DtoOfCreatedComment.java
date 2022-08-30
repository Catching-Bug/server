package com.catchbug.server.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class DtoOfCreatedComment {
    private Long commentId;
    private String content;
    private LocalDateTime commentedAt;
    private String commenterNickname;
}
