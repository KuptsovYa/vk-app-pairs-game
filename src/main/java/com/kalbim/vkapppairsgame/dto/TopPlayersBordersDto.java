package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopPlayersBordersDto {

    @JsonProperty("left")
    private Integer left;

    @JsonProperty("right")
    private Integer right;

    @JsonProperty("friendsList")
    private List<String> friendsList;

    @JsonProperty("vkToken")
    private String vkToken;
}
