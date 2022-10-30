package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.SingleCircumstanceUpdateDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.dto.UserPlaceInLeadBoardDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserReposImpl implements UserRepos {

    private final JdbcOperations jdbcOperations;

    @Autowired
    public UserReposImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public UsersEntity getAllUserData(String userId) {
        String sql = "Select user, coins, gameCount, notifications, circs from users where user = ?;";
        Object[] params = new Object[]{userId};
        UsersEntity usersEntity = null;
        try {
            usersEntity = getJdbcOperations().queryForObject(sql,
                    params, new BeanPropertyRowMapper<>(UsersEntity.class));
        } catch (JDBCException | EmptyResultDataAccessException exception) {
            String request = "insert into users(user, coins, gameCount, notifications, circs) values(?, ?, ?, ?, ?);";
            Object[] paramsToInsert = new Object[]{userId, 0, 2, 0, "00000"};
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
        String updateRequest = "Update users set coins = ?, gameCount = gameCount - 1 where user = ?;";
        getJdbcOperations().update(updateRequest, params);
    }

    public List<UsersEntity> getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) {
        Object[] params = new Object[]{topPlayersBordersDto.getLeft(),
                topPlayersBordersDto.getRight()};
        String selectRequest = "Select user, coins, gameCount, notifications, circs from users order by coins desc limit ?,?;";
        return getJdbcOperations().query(selectRequest, params,
                (rs, rowNum) -> new UsersEntity(
                        Integer.parseInt(rs.getString("user")),
                        Integer.parseInt(rs.getString("coins")),
                        Integer.parseInt(rs.getString("gameCount")),
                        Integer.parseInt(rs.getString("notifications")),
                        rs.getString("circs")
                ));
    }

    public List<UsersEntity> getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto) {
        String selectFirstPart = "Select user, coins, gameCount, notifications, circs from users where user in (";
        String selectSecondPart = ") order by coins desc limit ?,?;";

        String resultList = topPlayersBordersDto.getFriendsList().stream().collect(Collectors.joining(","));

        Object[] params = new Object[]{topPlayersBordersDto.getLeft(), topPlayersBordersDto.getRight()};

        return getJdbcOperations().query(selectFirstPart + resultList + selectSecondPart, params,
                (rs, rowNum) -> new UsersEntity(
                        Integer.parseInt(rs.getString("user")),
                        Integer.parseInt(rs.getString("coins")),
                        Integer.parseInt(rs.getString("gameCount")),
                        Integer.parseInt(rs.getString("notifications")),
                        rs.getString("circs")
                ));
    }

    @Override
    public void updateGamesCount() {
        String updateQuery = "Update users set gameCount = gameCount + 1 where gameCount < 2;";
        getJdbcOperations().update(updateQuery);
    }

    public List<UsersEntity> getAllPlayersWithNotifications() {
        String selectQuery = "Select user, coins, gameCount, notifications, circs from users where notifications = 1;";
        return getJdbcOperations().query(selectQuery, (rs, rowNum) -> new UsersEntity(
                Integer.parseInt(rs.getString("user")),
                Integer.parseInt(rs.getString("coins")),
                Integer.parseInt(rs.getString("gameCount")),
                Integer.parseInt(rs.getString("notifications")),
                rs.getString("circs")
        ));
    }

    @Override
    public void updateNotificationsStatus(UserDto userDto) {
        String updateQuery = "Update users set notifications = ? where user = ?;";
        Object[] params = new Object[]{userDto.getNotifications(), userDto.getUserId()};
        getJdbcOperations().update(updateQuery, params);
    }

    @Override
    public void updateCircumstances(SingleCircumstanceUpdateDto userDto) {
        String updateQuery = "Update users set circs = ? where user = ?";
        Object[] params = new Object[]{userDto.getCircumstance(), userDto.getUserId()};
        getJdbcOperations().update(updateQuery, params);
    }

    //

    public List<Map<String, Object>> getUserPlaceInTotalLeaderboard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        String selectQuery = "select * from (select user, " +
                "(@row_number:=@row_number + 1) as number from users, (SELECT @row_number:=0) as temp order by coins desc)" +
                " as result where result.user = ?";
        Object[] params = new Object[]{userPlaceInLeadBoardDto.getUserId()};
        return getJdbcOperations().queryForList(selectQuery, params);
    }

    public List<Map<String, Object>> getUserPlaceInFriendsLeaderboard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        String selectQuery = "select * from (select user, (@row_number:=@row_number + 1) as number " +
                "from users, (SELECT @row_number:=0) as temp where user in (";
        String friendsList = userPlaceInLeadBoardDto.getFriendsList().stream().collect(Collectors.joining(","));
        Object[] params = new Object[]{userPlaceInLeadBoardDto.getUserId()};
        return getJdbcOperations().queryForList(selectQuery + friendsList + ") order by coins desc) as result where result.user = ?", params);
    }

    public Integer getTotalPlayers(List<String> friendsList) {
        String select = "select count(*) from users where user in (";
        String resultList = friendsList.stream().collect(Collectors.joining(","));
        return getJdbcOperations().queryForObject(select + resultList + ");", Integer.class);
    }

    public Integer getTotalPlayers() {
        String select = "select count(*) from users";
        return getJdbcOperations().queryForObject(select, Integer.class);
    }

    public JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
