package com.kalbim.vkapppairsgame.repos;


import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;

public interface PromoRepos {

    PromoEntity getPromoByCoins(PlayerCoinsDto playerCoinsDto);
}
