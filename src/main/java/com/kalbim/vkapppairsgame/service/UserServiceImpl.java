package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
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
                .gameCount(String.valueOf(usersEntity.getGameCount()))
                .build();
    }

    public UserDto updateUserData(UserDto userDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(userDto.getUserId());
        if (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) {
            userDto.setCoins(userDto.getCoins() + 10);
        }
        userRepos.updateUserData(userDto);
        UsersEntity updatedUsersEntity = userRepos.getAllUserData(userDto.getUserId());
        return UserDto.builder()
                .userId(String.valueOf(updatedUsersEntity.getUser()))
                .gameCount(String.valueOf(updatedUsersEntity.getGameCount()))
                .coins(String.valueOf(updatedUsersEntity.getCoins())).build();
    }

    public TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) {
        List<UsersEntity> entityList = userRepos.getTopPlayers(topPlayersBordersDto);
        return convertFromEntityToDto(entityList);
    }

    public TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto) {
        List<UsersEntity> entityList = userRepos.getTopPlayersFromFriends(topPlayersBordersDto);
        return convertFromEntityToDto(entityList);
    }

    private TopPlayersDto convertFromEntityToDto(List<UsersEntity> list) {
        List<UserDto> dtoList = list.stream()
                .map(entity ->
                        UserDto.builder()
                                .coins(String.valueOf(entity.getCoins()))
                                .gameCount(String.valueOf(entity.getGameCount()))
                                .userId(String.valueOf(entity.getUser()))
                                .build()
                ).collect(Collectors.toList());
        return TopPlayersDto.builder().users(dtoList).build();
    }

    public void updateGameCount() {
        userRepos.updateGamesCount();
    }
}
