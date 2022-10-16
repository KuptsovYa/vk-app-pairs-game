package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("coins")
    private String coins;
}
