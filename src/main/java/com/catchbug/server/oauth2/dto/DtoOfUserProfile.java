package com.catchbug.server.oauth2.dto;

import com.catchbug.server.member.Gender;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Generated;

/**
 * <h1>DtoOfUserProfile</h1>
 * <p>
 *     Dto of Oauth2 User Profile
 * </p>
 * <p>
 *     Oauth2 서버로 부터 받은 사용자 프로필 DTO
 * </p>
 *
 * @see com.catchbug.server.oauth2.util.Oauth2RequestUtil
 * @author younghoCha
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DtoOfUserProfile {

    /**
     * Oauth2로 부터 받은 사용자 id
     */
    private Long id;

    /**
     * 사용자 속성 값
     */
    private Properties properties;

    /**
     * 사용자 데이터
     */
    private KakaoAccount kakaoAccount;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class KakaoAccount {


        private Profile profile;

        private Gender gender;

        public void updateGender(Gender gender){
            this.gender = gender;
        }

    }



    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Profile {

        private String nickname;

    }


    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Properties {

        private String nickname;

    }

}

