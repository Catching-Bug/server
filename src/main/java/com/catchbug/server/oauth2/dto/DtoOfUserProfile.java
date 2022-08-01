package com.catchbug.server.oauth2.dto;

import com.catchbug.server.member.Gender;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Generated;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DtoOfUserProfile {

    private Long id;
    private Properties properties;
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

