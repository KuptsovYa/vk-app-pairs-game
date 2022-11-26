package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.dto.UserPromoDto;

public interface PromoServiceImpl {

    PromoDto returnPromo(PlayerCoinsDto playerCoinsDto);

    UserPromoDto getUsersPromoList(UserPromoDto userPromoDto);
}
