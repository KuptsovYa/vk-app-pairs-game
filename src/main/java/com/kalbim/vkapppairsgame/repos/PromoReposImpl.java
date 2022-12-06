package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.dto.UserPromoDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                " and used = 0 limit 1";
        Object[] params = new Object[]{playerCoinsDto.getCoins()};
        PromoEntity promoEntity = getJdbcOperations().queryForObject(selectRequest,
                params, new BeanPropertyRowMapper<>(PromoEntity.class));

        params = new Object[] {playerCoinsDto.getUserId(), promoEntity.getIdpromo()};
        String updatePromoRequest = "Update promo set used = ? where idPromo = ?;";
        getJdbcOperations().update(updatePromoRequest, params);

        params = new Object[]{promoEntity.getPrice(), playerCoinsDto.getUserId()};
        String updateUsersRequest = "update users set coins = coins - ? where user = ?;";
        getJdbcOperations().update(updateUsersRequest, params);
        return promoEntity;
    }

    public List<PromoEntity> getUsersPromoList(UserPromoDto userPromoDto) {
        String selectRequest = "Select idpromo, promo, price, used from promo where used = ?";
        Object[] params = new Object[]{userPromoDto.getUserId()};
        return getJdbcOperations().query(selectRequest, params,
                (rs, rowNum) -> new PromoEntity(
                        Integer.parseInt(rs.getString("idpromo")),
                        rs.getString("promo"),
                        Integer.parseInt(rs.getString("price")),
                        Integer.parseInt(rs.getString("used"))
                ));
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
