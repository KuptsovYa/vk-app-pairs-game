package com.kalbim.vkapppairsgame.repos;


import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.dto.UserPromoDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;

import java.util.List;

public interface PromoRepos {

    PromoEntity getPromoByCoins(PlayerCoinsDto playerCoinsDto);
    List<PromoEntity> getUsersPromoList(UserPromoDto userPromoDto);
}
