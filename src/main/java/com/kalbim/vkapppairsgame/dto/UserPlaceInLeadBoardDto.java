package com.kalbim.vkapppairsgame.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPlaceInLeadBoardDto {

    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("totalUsersCount")
    private String totalUsersCount;
    @JsonProperty("friendsList")
    private List<String> friendsList;
    @JsonProperty("vkToken")
    private String vkToken;
}
