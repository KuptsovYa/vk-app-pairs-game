package com.kalbim.vkapppairsgame.repos;

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
    public PromoEntity getPromoByCoins(String coinsAmount) {
        String request = "Select idpromo, promo, price, used where price = ?" +
                " and used <> '1'";
        Object[] params = new Object[]{coinsAmount};
        return getJdbcOperations().queryForObject(request,
                params, new BeanPropertyRowMapper<>(PromoEntity.class));
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
