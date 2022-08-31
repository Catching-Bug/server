package com.catchbug.server.comment.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DtoOfGetComments {

    private List<DtoOfGetComment> comments;
    private int size;

}
