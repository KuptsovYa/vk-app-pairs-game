package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class TopPlayersDto {

    @JsonProperty
    private List<UserDto> users;
}
