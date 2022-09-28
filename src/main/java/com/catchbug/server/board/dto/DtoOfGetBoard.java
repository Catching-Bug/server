package com.catchbug.server.board.dto;

import com.catchbug.server.board.Status;
import com.catchbug.server.employ.Employ;
import com.catchbug.server.employ.dto.DtoOfGetEmploy;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
/**
 * <h1>DtoOfGetBoard</h1>
 * <p>
 *     Dto of inquire board
 * </p>
 * <p>
 *     게시글 조회 시, 응답되는 게시글 DTO
 * </p>
 *
 * @see com.catchbug.server.board.BoardService
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfGetBoard {

    /**
     * board id
     */
    private Long id;
    /**
     * 조회된 위도
     */
    private double latitude;

    /**
     * 조회된 경도
     */
    private double longitude;

    /**
     * 조회된 도, 시
     */
    private String region;

    /**
     * 조회된 시, 군, 구
     */
    private String city;

    /**
     * 조회된 읍, 면, 동
     */
    private String town;

    /**
     * 조회된 상세 주소
     */
    private String detailLocation;

    /**
     * 조회된 글 제목
     */
    private String roomTitle;

    /**
     * 조회된 글 내용
     */
    private String roomContent;

    /**
     * 조회된 글 작성자 nickname
     */
    private String creatorNickname;

    /**
     * 방 생성 시간
     */
    private LocalDateTime createdTime;

    /**
     * 생성자 id
     */
    private Long creatorId;

    /**
     * 글 매칭 상황
     */
    private Status status;

    /**
     * 글 만료 시간
     */
    private LocalDateTime expiryTime;

    /**
     * 고용정보 Dto
     */
    private DtoOfGetEmploy employ;

    public void updateEmploy(Employ employ){
        this.employ = DtoOfGetEmploy.builder()
                .employId(employ.getId())
                .employeeId(employ.getEmployee().getId())
                .employerId(employ.getEmployer().getId())
                .employeeNickname(employ.getEmployee().getNickname())
                .employerNickname(employ.getEmployer().getNickname())
                .build();
    }

}

