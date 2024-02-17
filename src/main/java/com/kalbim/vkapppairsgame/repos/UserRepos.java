package com.kalbim.vkapppairsgame.repos;

import com.kalbim.vkapppairsgame.dto.SingleCircumstanceUpdateDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersBordersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.dto.UserPlaceInLeadBoardDto;
import com.kalbim.vkapppairsgame.entity.LeaderBoardEntity;
import com.kalbim.vkapppairsgame.entity.UsersEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface UserRepos {

    UsersEntity getAllUserData(String userId);
    void updateUserData(UserDto userDto, String coins);
    List<LeaderBoardEntity> getTopPlayers(TopPlayersBordersDto topPlayersBordersDto);
    List<LeaderBoardEntity> getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto);
    void updateGamesCount();
    List<UsersEntity> getAllPlayersWithNotifications();
    void updateNotificationsStatus(UserDto userDto);
    void updateCircumstances(SingleCircumstanceUpdateDto userDto);
    List<Map<String, Object>> getUserPlaceInTotalLeaderboard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto);
    Integer getTotalPlayers(List<String> friendsList);
    Integer getTotalPlayers();
    List<Map<String, Object>> getUserPlaceInFriendsLeaderboard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto);


}
