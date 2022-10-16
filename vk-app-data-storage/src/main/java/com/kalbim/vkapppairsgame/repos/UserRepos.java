package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;

public interface UserRepos {

    UsersEntity getAllUserData(String userId);
    void updateUserData(UserDto userDto);
}
