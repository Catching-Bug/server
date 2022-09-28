package com.catchbug.server.comment.dto;

import java.time.LocalDateTime;

public interface DtoOfGetComment {

    String getContent();
    LocalDateTime getCommentedAt();
    String getCommenterNickname();
    Long getId();

}
