package com.kalbim.vkapppairsgame.controllers;


import com.kalbim.vkapppairsgame.dto.*;
import com.kalbim.vkapppairsgame.service.PromoService;
import com.kalbim.vkapppairsgame.service.TimeService;
import com.kalbim.vkapppairsgame.service.UserService;
import com.kalbim.vkapppairsgame.vk.VkApiClass;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class RestRequestHandler {

    private final UserService userService;
    private final PromoService promoService;
    private final TimeService timeService;
    private final VkApiClass vkApiClass;

    @Autowired
    public RestRequestHandler(UserService userService, PromoService promoService, TimeService timeService, VkApiClass vkApiClass) {
        this.userService = userService;
        this.promoService = promoService;
        this.timeService = timeService;
        this.vkApiClass = vkApiClass;
    }

    @PostMapping("v1/api/getUserData/{vkUserId}")
    @Transactional
    public UserDto getAllUserData(@PathVariable String vkUserId, @RequestBody UserDto userDto) {
        try {
            userDto.setUserId(vkApiClass.checkForCorrectUserByKey(userDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.getAllDataOfUser(userDto.getUserId());
    }

    @PostMapping("v1/api/getPromo/")
    @Transactional
    public PromoDto getPromoByCoins(@RequestBody PlayerCoinsDto playerCoinsDto) {
        try {
            playerCoinsDto.setUserId(vkApiClass.checkForCorrectUserByKey(playerCoinsDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return promoService.returnPromo(playerCoinsDto);
    }

    @PostMapping("v1/api/up")
    @Transactional
    public UserDto updateUserData(@RequestBody UserDto userDto) {
        try {
            userDto.setUserId(vkApiClass.checkForCorrectUserByKey(userDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.updateUserData(userDto);
    }

    @PostMapping("v1/api/getTopPlayers")
    @Transactional
    public TopPlayersDto getTopPlayers(@RequestBody TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        try {
            vkApiClass.checkForCorrectUserByKey(topPlayersBordersDto.getVkToken());
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.getTopPlayers(topPlayersBordersDto);
    }

    @PostMapping("v1/api/getTopPlayersFriends")
    @Transactional
    public TopPlayersDto getTopPlayersFromFriends(@RequestBody TopPlayersBordersDto topPlayersBordersDto) throws ClientException, ApiException {
        try {
            vkApiClass.checkForCorrectUserByKey(topPlayersBordersDto.getVkToken());
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.getTopPlayersFromFriends(topPlayersBordersDto);
    }

    @PostMapping("v1/api/updateNotificationStatus")
    @Transactional
    public UserDto updateNotificationStatus(@RequestBody UserDto userDto) {
        try {
            userDto.setUserId(vkApiClass.checkForCorrectUserByKey(userDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        userService.updateNotificationStatus(userDto);
        return userDto;
    }

    @PostMapping("v1/api/updateCirc")
    @Transactional
    public UserDto updateCircumstances(@RequestBody SingleCircumstanceUpdateDto scuDto) {
        try {
            scuDto.setUserId(vkApiClass.checkForCorrectUserByKey(scuDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.updateCircumstances(scuDto);
    }

    @GetMapping("v1/api/serverTime")
    public ServerTimeDto getServerTime() {
        return timeService.returnServerTime();
    }

    @PostMapping("/v1/api/getPlaceInTop")
    public UserPlaceInLeadBoardDto getUserPlaceInLeaderBoard(@RequestBody UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        try {
            userPlaceInLeadBoardDto.setUserId(vkApiClass.checkForCorrectUserByKey(userPlaceInLeadBoardDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.getUserPlaceInLeaderBoard(userPlaceInLeadBoardDto);
    }

    @PostMapping("/v1/api/getPlaceInTopFriends")
    public UserPlaceInLeadBoardDto getUserPlaceInFriendsLeaderBoard(@RequestBody UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        try {
            userPlaceInLeadBoardDto.setUserId(vkApiClass.checkForCorrectUserByKey(userPlaceInLeadBoardDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return userService.getUserPlaceInFriendsLeaderBoard(userPlaceInLeadBoardDto);
    }

    @PostMapping("/v1/api/getUserPromoCodes")
    public UserPromoDto getPromoCodesByUser(@RequestBody UserPromoDto userPromoDto) {
        try {
            userPromoDto.setUserId(vkApiClass.checkForCorrectUserByKey(userPromoDto.getVkToken()));
        } catch (Exception e) {
            throw new InvalidDataAccessApiUsageException(e.getMessage());
        }
        return promoService.getUsersPromoList(userPromoDto);
    }
}
