package com.catchbug.server.member;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <h1>Gender</h1>
 * <p>
 *     Gender of Member
 * </p>
 * <p>
 *     사용자 성별 열거형 클래스
 * </p>
 *
 * @see com.catchbug.server.member.Member
 * @author younghoCha
 */
public enum Gender {
    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE,
    NONE;
}
