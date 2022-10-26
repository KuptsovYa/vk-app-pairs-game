package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;

public interface PromoServiceImpl {

    PromoDto returnPromo(PlayerCoinsDto playerCoinsDto);
}
