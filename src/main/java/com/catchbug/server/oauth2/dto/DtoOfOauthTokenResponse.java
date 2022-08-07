package com.catchbug.server.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * <h1>DtoOfOauthTokenResponse</h1>
 * <p>
 *     Dto Of getting AccessToken From Oauth Server
 * </p>
 * <p>
 *     Oauth2 서버로 부터 받은 엑세스 토큰 DTO
 * </p>
 *
 * @see com.catchbug.server.oauth2.util.Oauth2RequestUtil
 * @author younghoCha
 */
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
