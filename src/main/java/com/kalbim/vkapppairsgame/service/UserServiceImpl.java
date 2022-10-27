package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import com.kalbim.vkapppairsgame.vk.VkApiClass;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
                .notifications(String.valueOf(usersEntity.getNotifications()))
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
                .coins(String.valueOf(updatedUsersEntity.getCoins()))
                .notifications(String.valueOf(usersEntity.getNotifications()))
                .build();
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


    public void sendNotifications() throws ClientException, ApiException {
        VkApiClass vkApiClass = new VkApiClass();

        List<UsersEntity> usersEntities = userRepos.getAllPlayersWithNotifications();
        Integer[] idsArray = usersEntities.stream().map(UsersEntity::getUser).toArray(Integer[]::new);
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i <= idsArray.length; i++) {
            idsList.add(idsArray[i]);
            if (i % 10 == 0) { //just try to not forget return value to 100
                vkApiClass.sendNotification(idsList);
                idsList.clear();
            }
            if (i == idsArray.length - 1) {
                vkApiClass.sendNotification(idsList);
                idsList.clear();
            }
        }
    }

    @Override
    public void updateNotificationStatus(UserDto userDto) {
        userRepos.updateNotificationsStatus(userDto);
    }

    public void updateGameCount() {
        userRepos.updateGamesCount();
    }
}
