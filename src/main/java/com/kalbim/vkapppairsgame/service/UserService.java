package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.SingleCircumstanceUpdateDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface UserService {

    UserDto getAllDataOfUser(String userId);
    UserDto updateUserData(UserDto userDto);
    TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto);
    TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto);
    void updateGameCount();
    void sendNotifications() throws ClientException, ApiException;
    void updateNotificationStatus(UserDto userDto);
    UserDto updateCircumstances(SingleCircumstanceUpdateDto singleCircumstanceUpdateDto);
}
