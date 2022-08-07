package com.catchbug.server.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DtoOfCreateBoard {

    @NotNull(message = "제목은 필수로 입력해야합니다.")
    @Size(min = 1, max = 10, message = "제목은 최소 1글자, 최대 10글자까지 입력가능합니다.")
    private String title;

    @NotNull(message = "내용은 최소 1글자 이상이어야 합니다.")
    private String content;

}
