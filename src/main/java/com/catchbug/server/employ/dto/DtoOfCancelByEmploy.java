package com.catchbug.server.employ.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfCancelByEmploy</h1>
 * <p>
 *     Dto Of Response after Cancel employ
 * </p>
 * <p>
 *     고용정보를 취소한 후의 응답 Dto
 * </p>
 *
 * @see com.catchbug.server.employ.EmployService
 * @see com.catchbug.server.employ.EmployController
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfCancelByEmploy {

    /**
     * 취소된 boardTitle
     */
    private String boardTitle;

    /**
     * 취소된 boardId
     */
    private Long boardId;

    /**
     * 취소된 고용정보의 피고용자 닉네임
     */
    private String employeeNickname;

    /**
     * 취소된 고용정보의 피고용자 id
     */
    private Long employeeId;

    /**
     * 취소된 고용정보의 고용자 닉네임
     */
    private String employerNickname;

    /**
     * 취소된 고용정보의 고용자 id
     */
    private Long employerId;
}
