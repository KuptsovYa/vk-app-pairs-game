package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("coins")
    private String coins;
    @JsonProperty("gameCount")
    private String gameCount;
    @JsonProperty("notifications")
    private String notifications;
}
