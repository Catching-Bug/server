package com.catchbug.server.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfCreateComment {

    private String content;
}
