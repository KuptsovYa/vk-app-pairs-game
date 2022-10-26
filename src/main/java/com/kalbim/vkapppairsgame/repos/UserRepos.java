package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;

import java.util.List;

public interface UserRepos {

    UsersEntity getAllUserData(String userId);
    void updateUserData(UserDto userDto);
    List<UsersEntity> getTopPlayers(TopPlayersBordersDto topPlayersBordersDto);
    void updateGamesCount();
}
