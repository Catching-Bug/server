package com.catchbug.server.employ.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfApplyEmploy</h1>
 * <p>
 *     Dto Of Response after Board Creation
 * </p>
 * <p>
 *     게시판 글을 생성된 후 응답될 DTO
 * </p>
 *
 * @see com.catchbug.server.employ.Employ
 * @see com.catchbug.server.employ.EmployController
 * @see com.catchbug.server.employ.EmployService
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfApplyEmploy {

    /**
     * 고용 정보가 생성된 게시글 id
     */
    private Long boardId;

    /**
     * 고용 정보가 생성된 고용자 닉네임
     */
    private String employerNickname;

    /**
     * 고용 정보가 생성된 피고용자 닉네임
     */
    private String employeeNickname;

}
