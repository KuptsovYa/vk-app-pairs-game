package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.*;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface UserService {

    UserDto getAllDataOfUser(String userId);
    UserDto updateUserData(UserDto userDto);
    TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException;
    TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException;
    void updateGameCount();
    void sendNotifications() throws ClientException, ApiException;
    void updateNotificationStatus(UserDto userDto);
    UserDto updateCircumstances(SingleCircumstanceUpdateDto singleCircumstanceUpdateDto);
    UserPlaceInLeadBoardDto getUserPlaceInLeaderBoard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto);
    UserPlaceInLeadBoardDto getUserPlaceInFriendsLeaderBoard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto);
}
