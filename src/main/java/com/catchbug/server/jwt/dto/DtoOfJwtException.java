package com.catchbug.server.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfJwtException {

    private String msg;
}
