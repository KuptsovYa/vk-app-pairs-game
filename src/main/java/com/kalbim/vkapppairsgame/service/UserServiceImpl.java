package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.*;
import com.kalbim.vkapppairsgame.entity.LeaderBoardEntity;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import com.kalbim.vkapppairsgame.vk.VkApiClass;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Integer CIRCS_COUNT = 3;

    private final UserRepos userRepos;
    private final VkApiClass vkApiClass;

    @Autowired
    public UserServiceImpl(UserRepos userRepos, VkApiClass vkApiClass) {
        this.userRepos = userRepos;
        this.vkApiClass = vkApiClass;
    }

    public UserDto getAllDataOfUser(String userId) {
        UsersEntity usersEntity = userRepos.getAllUserData(userId);
        return userDtoAllFieldsBuilder(usersEntity);
    }

    public UserDto updateUserData(UserDto userDto) {
        UsersEntity usersEntity = userRepos.getAllUserData(userDto.getUserId());

        if (userDto.getCircs() != null) {
            String circs = usersEntity.getCircs();
            try {
                StringBuilder builder = new StringBuilder(circs);
                builder.insert(Integer.parseInt(userDto.getCircs()), '1');
                circs = builder.substring(CIRCS_COUNT);
                if (circs.length() > 3) throw new RuntimeException();
            } catch (RuntimeException e) {
                return userDto;
            }
            if (sumOfCircsInString(circs) > CIRCS_COUNT || circs.length() > CIRCS_COUNT) {
                return userDto;
            }
            return updateCoinsFromCircs(userDto, usersEntity);
        }

        return updateCoinsFromGames(userDto, usersEntity);
    }

    private UserDto updateCoinsFromCircs(UserDto userDto, UsersEntity usersEntity) {
        if (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) {
            userDto.setCoins(String.valueOf(usersEntity.getCoins() + 10));
        }
        userDto.setGameCount("0");
        userRepos.updateUserData(userDto, (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) ? "10"
                : String.valueOf(Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins()));
        return userDtoAllFieldsBuilder(userRepos.getAllUserData(userDto.getUserId()));
    }

    private UserDto updateCoinsFromGames(UserDto userDto, UsersEntity usersEntity) {
        //first case with default update coins
        if (usersEntity.getGameCount() <= 0) {
            return userDto;
        }
        if (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) {
            userDto.setCoins(String.valueOf(usersEntity.getCoins() + 10));
        }
        //Its must be one coz in update we are making gameCount - value that comes from this method
        userDto.setGameCount("1");
        userRepos.updateUserData(userDto, (Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins() > 10) ? "10"
                : String.valueOf(Integer.parseInt(userDto.getCoins()) - usersEntity.getCoins()));
        return userDtoAllFieldsBuilder(userRepos.getAllUserData(userDto.getUserId()));
    }

    private int sumOfCircsInString(String circs) {
        int sum=0;
        for (int i = 0; i < circs.length(); i++) {
            if(Character.isDigit(circs.charAt(i)))
                sum=sum+Character.getNumericValue(circs.charAt(i));
        }
        return sum;
    }

    public TopPlayersDto getTopPlayers(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        List<LeaderBoardEntity> entityList = userRepos.getTopPlayers(topPlayersBordersDto);
        List<GetResponse> responseList = vkApiClass.getUsers(entityList
                .stream()
                .map(e -> String.valueOf(e.getUserId()))
                .collect(Collectors.toList()));
        return convertFromEntityToDto(responseList, entityList);
    }

    public TopPlayersDto getTopPlayersFromFriends(TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        List<LeaderBoardEntity> entityList = userRepos.getTopPlayersFromFriends(topPlayersBordersDto);
        List<GetResponse> responseList = vkApiClass.getUsers(entityList
                .stream()
                .map(e -> String.valueOf(e.getUserId()))
                .collect(Collectors.toList()));

        return convertFromEntityToDto(responseList, entityList);
    }

    private TopPlayersDto convertFromEntityToDto(List<GetResponse> list, List<LeaderBoardEntity> entityList) {
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
                    Optional<LeaderBoardEntity> entity = entityList.stream().filter(dbEntity -> userId == dbEntity.getUserId()).findFirst();
                    if(entity.isPresent()) {
                        LeaderBoardEntity resEntity = entity.get();
                        elem.setCoins(resEntity.getCoins());
                    }
                }
        );
        return TopPlayersDto.builder().users(playerInLeaderBoardList).build();
    }

    public void sendNotifications() throws ClientException, ApiException {
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
