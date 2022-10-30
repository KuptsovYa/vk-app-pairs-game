package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class PromoReposImpl implements PromoRepos{

    private final JdbcOperations jdbcOperations;

    @Autowired
    public PromoReposImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public PromoEntity getPromoByCoins(PlayerCoinsDto playerCoinsDto) {
        String selectRequest = "Select idpromo, promo, price, used from promo where price = ?" +
                " and used <> 1";
        Object[] params = new Object[]{playerCoinsDto.getCoins()};
        PromoEntity promoEntity = getJdbcOperations().queryForObject(selectRequest,
                params, new BeanPropertyRowMapper<>(PromoEntity.class));

        params[0] = promoEntity.getIdpromo();
        String updatePromoRequest = "Update promo set used = 1 where idPromo = ?;";
        getJdbcOperations().update(updatePromoRequest, params);
        String updateUsersRequest = "update users set coins = coins - ? where user = ?;";
        params = new Object[]{promoEntity.getPrice(), playerCoinsDto.getUserId()};
        getJdbcOperations().update(updateUsersRequest, params);
        return promoEntity;
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
