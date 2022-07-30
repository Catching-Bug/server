package com.catchbug.server.oauth2;

import com.catchbug.server.oauth2.dto.DtoOfUserProfile;

import java.util.Arrays;
import java.util.Map;


    public enum OauthAttributes {

        KAKAO("google") {
            @Override
            public DtoOfUserProfile of(Map<String, Object> attributes) {
                return DtoOfUserProfile.builder()
                        .name((String) attributes.get("name"))
                        .build();
            }
        };

        private final String providerName;

        OauthAttributes(String name) {
            this.providerName = name;
        }

        public static DtoOfUserProfile extract(String providerName, Map<String, Object> attributes) {
            return Arrays.stream(values())
                    .filter(provider -> providerName.equals(provider.providerName))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new)
                    .of(attributes);
        }

        public abstract DtoOfUserProfile of(Map<String, Object> attributes);
    }

