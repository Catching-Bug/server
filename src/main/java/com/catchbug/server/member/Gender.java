package com.catchbug.server.member;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Gender {
    @JsonProperty("male")
    MALE,
    @JsonProperty("female")
    FEMALE,
    NONE;
}
