package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
        UsersEntity usersEntity = getJdbcOperations().queryForObject(sql,
                params, new BeanPropertyRowMapper<>(UsersEntity.class));
        if (usersEntity == null) {
            String request = "insert into users values(?, ?)";
            Object[] paramsToInsert = new Object[]{userId, 0};
            getJdbcOperations().update(request, paramsToInsert);
            usersEntity = new UsersEntity();
            usersEntity.setUser(Integer.parseInt(userId));
        }
        return usersEntity;
    }

    @Override
    public void updateUserData(UserDto userDto) {
        Object[] params = new Object[]{userDto.getCoins(),
                userDto.getDate(),
                userDto.getUserId()};
        String updateRequest = "update users set coins = ? and lastGameTimestamp = ? where user = ?";
        getJdbcOperations().update(updateRequest, params);
    }

    public List<UsersEntity> getTopPlayers() {
        String selectRequest = "Select user, coins, lastGameTimestamp, gameCount from users order by coins DESC limit 100";
        List<UsersEntity> list = new ArrayList<>();
        getJdbcOperations().queryForList(selectRequest, list);
        return list;
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
