package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.*;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import com.kalbim.vkapppairsgame.vk.VkApiClass;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepos userRepos;
    private final VkApiClass vkApiClient = new VkApiClass();

    @Autowired
    public UserServiceImpl(UserRepos userRepos) {
        this.userRepos = userRepos;
    }

    public UserDto getAllDataOfUser(String userId) {
        UsersEntity usersEntity = userRepos.getAllUserData(userId);
        VkApiClass vkApiClient = new VkApiClass();
        return userDtoAllFieldsBuilder(usersEntity);
    }

    public UserDto updateUserData(UserDto userDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(userDto.getUserId());
        if (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) {
            userDto.setCoins(userDto.getCoins() + 10);
        }
        userRepos.updateUserData(userDto);
        return userDtoAllFieldsBuilder(userRepos.getAllUserData(userDto.getUserId()));
    }

    public TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        List<UsersEntity> entityList = userRepos.getTopPlayers(topPlayersBordersDto);
        List<GetResponse> responseList = vkApiClient.getUsers(entityList
                .stream()
                .map(e -> String.valueOf(e.getUser()))
                .collect(Collectors.toList()));
        return convertFromEntityToDto(responseList, entityList);
    }

    public TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        List<UsersEntity> entityList = userRepos.getTopPlayersFromFriends(topPlayersBordersDto);
        List<GetResponse> responseList = vkApiClient.getUsers(entityList
                .stream()
                .map(e -> String.valueOf(e.getUser()))
                .collect(Collectors.toList()));

        return convertFromEntityToDto(responseList, entityList);
    }

    private TopPlayersDto convertFromEntityToDto(List<GetResponse> list, List<UsersEntity> entityList) {
        List<PlayerInLeaderBoard> playerInLeaderBoardList = list.stream().map(e ->
                PlayerInLeaderBoard.builder()
                .firstName(e.getFirstNameNom())
                .secondName(e.getLastNameNom())
                .photo(e.getPhoto100().toString())
                .id(e.getId())
                .build()
        ).collect(Collectors.toList());

        playerInLeaderBoardList.forEach(
                elem -> {
                    Integer userId = elem.getId();
                    Optional<UsersEntity> entity = entityList.stream().filter(dbEntity -> userId == dbEntity.getUser()).findFirst();
                    if(entity.isPresent()) {
                        UsersEntity resEntity = entity.get();
                        elem.setCoins(resEntity.getCoins());
                    }
                }
        );
        return TopPlayersDto.builder().users(playerInLeaderBoardList).build();
    }

    public void sendNotifications() throws ClientException, ApiException {
        VkApiClass vkApiClass = new VkApiClass();

        List<UsersEntity> usersEntities = userRepos.getAllPlayersWithNotifications();
        Integer[] idsArray = usersEntities.stream().map(UsersEntity::getUser).toArray(Integer[]::new);
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i <= idsArray.length; i++) {
            idsList.add(idsArray[i]);
            if (i % 100 == 0) { //just try to not forget return value to 100
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
    public UserDto updateCircumstances(SingleCircumstanceUpdateDto singleCircumstanceUpdateDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(singleCircumstanceUpdateDto.getUserId());
        StringBuilder userCircs = new StringBuilder(usersEntity.getCircs());
        userCircs.setCharAt((Integer.parseInt(singleCircumstanceUpdateDto.getCircumstance())), '1');
        singleCircumstanceUpdateDto.setCircumstance(userCircs.toString());
        userRepos.updateCircumstances(singleCircumstanceUpdateDto);
        return userDtoAllFieldsBuilder(userRepos.getAllUserData(singleCircumstanceUpdateDto.getUserId()));
    }

    private UserDto userDtoAllFieldsBuilder(UsersEntity entity) {
        return UserDto.builder()
                .userId(String.valueOf(entity.getUser()))
                .gameCount(String.valueOf(entity.getGameCount()))
                .coins(String.valueOf(entity.getCoins()))
                .notifications(String.valueOf(entity.getNotifications()))
                .circs(entity.getCircs())
                .build();
    }

    @Override
    public void updateNotificationStatus(UserDto userDto) {
        userRepos.updateNotificationsStatus(userDto);
    }

    @Override
    public UserPlaceInLeadBoardDto getUserPlaceInLeaderBoard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        userPlaceInLeadBoardDto.setOrderNumber(String.valueOf(userRepos.getUserPlaceInTotalLeaderboard(
                userPlaceInLeadBoardDto).get(0).get("number")).replace(".0", ""));
        userPlaceInLeadBoardDto.setTotalUsersCount(String.valueOf(userRepos.getTotalPlayers()));
        return userPlaceInLeadBoardDto;
    }

    public UserPlaceInLeadBoardDto getUserPlaceInFriendsLeaderBoard(UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        userPlaceInLeadBoardDto.setOrderNumber(String.valueOf(userRepos.getUserPlaceInFriendsLeaderboard(
                userPlaceInLeadBoardDto).get(0).get("number")).replace(".0", ""));
        userPlaceInLeadBoardDto.setTotalUsersCount(String.valueOf(userRepos.getTotalPlayers(userPlaceInLeadBoardDto.getFriendsList())));
        return userPlaceInLeadBoardDto;
    }

    public void updateGameCount() {
        userRepos.updateGamesCount();
    }
}
