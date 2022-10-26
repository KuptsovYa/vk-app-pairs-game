package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;

public interface UserService {

    UserDto getAllDataOfUser(String userId);
    UserDto updateUserData(UserDto userDto);
    TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto);
    TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto);
    void updateGameCount();
}
