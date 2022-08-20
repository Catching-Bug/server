package com.catchbug.server.board.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DtoOfGetCityCount {

    private String cityName;
    private int count;
}
