package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepos userRepos;

    @Autowired
    public UserServiceImpl(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    public UserDto getAllDataOfUser(String userId) {
        UsersEntity usersEntity = userRepos.getAllUserData(userId);
        return UserDto.builder()
                .coins(String.valueOf(usersEntity.getCoins()))
                .userId(String.valueOf(usersEntity.getUser()))
                .build();
    }

    public void updateUserData(UserDto userDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(userDto.getUserId());
        if (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) {
            userDto.setCoins(userDto.getCoins() + 10);
        }
        userRepos.updateUserData(userDto);
    }

    public TopPlayersDto getTopPlayers() {
        List<UsersEntity> entityList = userRepos.getTopPlayers();
        List<UserDto> dtoList = entityList.stream()
                .map(entity ->
                        UserDto.builder()
                                .coins(String.valueOf(entity.getCoins()))
                                .gameCount(String.valueOf(entity.getGameCount()))
                                .userId(String.valueOf(entity.getUser()))
                                .date(entity.getLastGameTimestamp())
                                .build()
                ).collect(Collectors.toList());
        return TopPlayersDto.builder().users(dtoList).build();
    }

    public void updateGameCount() {
        userRepos.updateGamesCount();
    }
}
