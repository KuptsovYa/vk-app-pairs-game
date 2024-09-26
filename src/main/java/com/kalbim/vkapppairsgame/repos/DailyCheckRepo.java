package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.entity.DailyCheckEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class DailyCheckRepo {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public DailyCheckRepo(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public DailyCheckEntity getCurrentLastLoginEntity(String user) {
        String selectQuery = "Select user, lastLoginDate, dayCount from dailyCheck where user = ?";
        Object[] params = new Object[]{user};
        DailyCheckEntity dailyCheckEntity = null;
        try {
            dailyCheckEntity = getJdbcOperations().queryForObject(selectQuery,
                    params, new BeanPropertyRowMapper<>(DailyCheckEntity.class));
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        return dailyCheckEntity;
    }

    public void insertNewLastLoginDate(String user, Timestamp timestamp, int dayCount) {
        String insertQuery = "INSERT INTO dailyCheck(user, lastLoginDate, dayCount) VALUES(?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE lastLoginDate = ?, dayCount = ?;";
        Object[] paramsToInsert = new Object[]{user, timestamp, dayCount, timestamp, dayCount};
        getJdbcOperations().update(insertQuery, paramsToInsert);
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
