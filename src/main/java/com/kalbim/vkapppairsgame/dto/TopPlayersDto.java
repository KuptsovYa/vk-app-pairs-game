package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopPlayersDto {

    @JsonProperty("users")
    private List<PlayerInLeaderBoard> users;

    @JsonProperty("vkToken")
    private String vkToken;
}
