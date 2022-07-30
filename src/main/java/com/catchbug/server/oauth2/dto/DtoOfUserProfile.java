package com.catchbug.server.oauth2.dto;

import com.catchbug.server.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DtoOfUserProfile {
    private final String email;
    private final String name;

    @Builder
    public DtoOfUserProfile(String email, String name) {
        this.email = email;
        this.name = name;

    }

    public Member toMember() {
        return Member.builder()
                .nickname(name)
                .build();
    }
}
