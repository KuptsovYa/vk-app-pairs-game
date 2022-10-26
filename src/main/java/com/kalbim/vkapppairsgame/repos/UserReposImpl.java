package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserReposImpl implements UserRepos {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public UserReposImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public UsersEntity getAllUserData(String userId) {
        String sql = "Select user, coins, lastGameTimestamp, gameCount from users where user = ?";
        Object[] params = new Object[]{userId};
        UsersEntity usersEntity = null;
        try {
            usersEntity = getJdbcOperations().queryForObject(sql,
                    params, new BeanPropertyRowMapper<>(UsersEntity.class));
        } catch (JDBCException | EmptyResultDataAccessException exception) {
            String request = "insert into users(user, coins, gameCount) values(?, ?, ?)";
            Object[] paramsToInsert = new Object[]{userId, 0, 2};
            getJdbcOperations().update(request, paramsToInsert);
            usersEntity = new UsersEntity();
            usersEntity.setUser(Integer.parseInt(userId));
        }
        return usersEntity;
    }

    @Override
    public void updateUserData(UserDto userDto) {
        Object[] params = new Object[]{userDto.getCoins(),
                userDto.getUserId()};
        String updateRequest = "Update users set coins = ?, gameCount = gameCount - 1 where user = ?";
        getJdbcOperations().update(updateRequest, params);
    }

    public List<UsersEntity> getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) {
        Object[] params = new Object[]{topPlayersBordersDto.getLeft(),
                topPlayersBordersDto.getRight()};
        String selectRequest = "Select user, coins, lastGameTimestamp, gameCount from users order by coins desc limit ?,?";
        List<UsersEntity> list = new ArrayList<>();
        getJdbcOperations().queryForList(selectRequest, params, list);
        return list;
    }

    @Override
    public void updateGamesCount() {
        String updateQuery = "Update users set gameCount = gameCount + 1 where gameCount < 2";
        getJdbcOperations().update(updateQuery);
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
