package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleCircumstanceUpdateDto {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("circumstance")
    private String circumstance;
    @JsonProperty("vkToken")
    private String vkToken;
}
