package com.catchbug.server.board;

import com.catchbug.server.board.dto.DtoOfBoard;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class DtoOfPage {

    private List<DtoOfBoard> dtoOfBoardList;
    private int size;
    private long offset;
}
