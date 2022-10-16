package com.kalbim.vkapppairsgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromoDto {
    @JsonProperty("promo")
    private String promo;
}
