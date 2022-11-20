package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerCoinsDto {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("coins")
    private String coins;
    @JsonProperty("vkToken")
    private String vkToken;
}
