package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerInLeaderBoard {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("secondName")
    private String secondName;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("coins")
    private Integer coins;

    @JsonProperty("id")
    private Integer id;
}
