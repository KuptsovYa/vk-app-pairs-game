package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;

public interface UserService {

    UserDto getAllDataOfUser(String userId);
    void updateUserData(UserDto userDto);
    TopPlayersDto getTopPlayers();
    public void updateGameCount();
}
