package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UserReposImpl implements UserRepos {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public UserReposImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public UsersEntity getAllUserData(String userId) {
        String sql = "Select * from users where user = ?";
        Object[] params = new Object[]{userId};
        return getJdbcOperations().queryForObject(sql,
                params, new BeanPropertyRowMapper<>(UsersEntity.class));
    }

    @Override
    public void updateUserData(UserDto userDto) {
        String sql = "Select * from users where user = ?";
        Object[] params = new Object[]{userDto.getUserId()};
        UsersEntity usersEntity = getJdbcOperations().queryForObject(sql,
                params, new BeanPropertyRowMapper<>(UsersEntity.class));
        if (usersEntity == null) {
            String request = "insert into users values(?, ?)";

        }// TODO

    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
