package com.catchbug.server.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
public class DtoOfOauthTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    @Builder
    public DtoOfOauthTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;

    }

    public DtoOfOauthTokenResponse(){
        ;
    }
}
