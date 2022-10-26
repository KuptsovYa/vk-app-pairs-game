package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.PromoRepos;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoService implements PromoServiceImpl{

    private final PromoRepos promoRepos;
    private final UserRepos userRepos;

    @Autowired
    public PromoService(PromoRepos promoRepos, UserRepos userRepos) {
        this.promoRepos = promoRepos;
        this.userRepos = userRepos;
    }

    @Override
    public PromoDto returnPromo(PlayerCoinsDto playerCoinsDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(playerCoinsDto.getUserId());
        if (usersEntity.getCoins() % Integer.parseInt(playerCoinsDto.getCoins()) < 100) {
            PromoEntity pe = promoRepos.getPromoByCoins(playerCoinsDto);
            return PromoDto.builder().promo(pe.getPromo()).build();
        }
        return PromoDto.builder().promo("").build();
    }
}
