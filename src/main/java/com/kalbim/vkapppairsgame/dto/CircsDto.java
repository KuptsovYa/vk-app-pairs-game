package com.kalbim.vkapppairsgame.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CircsDto implements Comparable<CircsDto>{
    private String firstCirc;
    private String secondCirc;
    private String thirdCirc;

    private CircsDto(String circsString) {
        for (int i = 0; i < circsString.length(); i++) {
            if (circsString.charAt(i) > '1') {
                throw new RuntimeException("Incorrect values provided");
            }
        }
        setFirstCirc(String.valueOf(circsString.charAt(0)));
        setFirstCirc(String.valueOf(circsString.charAt(1)));
        setFirstCirc(String.valueOf(circsString.charAt(2)));
    }

    @Override
    public int compareTo(CircsDto o) {
        if (this.firstCirc.equals(o.getFirstCirc()) || this.secondCirc.equals(o.getSecondCirc()) || this.thirdCirc.equals(o.getThirdCirc())) {
            return 1;
        } else {
            return 0;
        }
    }
}
