package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPromoDto {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("promoList")
    private List<PromoDto> promoList;

    @JsonProperty("vkToken")
    private String vkToken;
}