package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

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
}
