package com.catchbug.server.member.dto;

import com.catchbug.server.member.Gender;
import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfGetMember</h1>
 * <p>
 *     Dto Of Getting Member
 * </p>
 * <p>
 *     사용자 1명의 정보 조회를 위한 Dto 클래스
 * </p>
 *
 * @see com.catchbug.server.member.MemberService
 * @author younghoCha
 */
@Builder
@Getter
public class DtoOfGetMember {

    private String nickname;
    private com.catchbug.server.member.Gender Gender;

}
