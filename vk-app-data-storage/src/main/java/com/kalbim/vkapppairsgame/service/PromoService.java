package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;
import com.kalbim.vkapppairsgame.repos.PromoRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoService implements PromoServiceImpl{

    private final PromoRepos promoRepos;

    @Autowired
    public PromoService(PromoRepos promoRepos) {
        this.promoRepos = promoRepos;
    }

    @Override
    public PromoDto returnPromo(String coinsAmount) {
        PromoEntity pe = promoRepos.getPromoByCoins(coinsAmount);
        return PromoDto.builder().promo(pe.getPromo()).build();
    }
}
