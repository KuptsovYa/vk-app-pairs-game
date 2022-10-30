package com.kalbim.vkapppairsgame.controllers;


import com.kalbim.vkapppairsgame.dto.*;
import com.kalbim.vkapppairsgame.service.PromoService;
import com.kalbim.vkapppairsgame.service.TimeService;
import com.kalbim.vkapppairsgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class RestRequestHandler {

    private final UserService userService;
    private final PromoService promoService;
    private final TimeService timeService;

    @Autowired
    public RestRequestHandler(UserService userService, PromoService promoService, TimeService timeService) {
        this.userService = userService;
        this.promoService = promoService;
        this.timeService = timeService;
    }

    @GetMapping("v1/api/getUserData/{vkUserId}")
    @Transactional
    public UserDto getAllUserData(@PathVariable String vkUserId) {
        return userService.getAllDataOfUser(vkUserId);
    }

    @PostMapping("v1/api/getPromo/")
    @Transactional
    public PromoDto getPromoByCoins(@RequestBody PlayerCoinsDto playerCoinsDto) {
        return promoService.returnPromo(playerCoinsDto);
    }

    @PostMapping("v1/api/up")
    @Transactional
    public UserDto updateUserData(@RequestBody UserDto userDto) {
        return userService.updateUserData(userDto);
    }

    @PostMapping("v1/api/getTopPlayers")
    @Transactional
    public TopPlayersDto getTopPlayers(@RequestBody TopPlayersBordersDto topPlayersBordersDto) {
        return userService.getTopPlayers(topPlayersBordersDto);
    }

    @PostMapping("v1/api/getTopPlayersFriends")
    @Transactional
    public TopPlayersDto getTopPlayersFromFriends(@RequestBody TopPlayersBordersDto topPlayersBordersDto) {
        return userService.getTopPlayersFromFriends(topPlayersBordersDto);
    }

    @PostMapping("v1/api/updateNotificationStatus")
    @Transactional
    public UserDto updateNotificationStatus(@RequestBody UserDto userDto) {
        userService.updateNotificationStatus(userDto);
        return userDto;
    }

    @PostMapping("v1/api/updateCirc")
    @Transactional
    public UserDto updateCircumstances(@RequestBody SingleCircumstanceUpdateDto scuDto) {
        return userService.updateCircumstances(scuDto);
    }

    @GetMapping("v1/api/serverTime")
    public ServerTimeDto getServerTime() {
        return timeService.returnServerTime();
    }

    @PostMapping("/v1/api/getPlaceInTop")
    public UserPlaceInLeadBoardDto getUserPlaceInLeaderBoard(@RequestBody UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        return userService.getUserPlaceInLeaderBoard(userPlaceInLeadBoardDto);
    }

    @PostMapping("/v1/api/getPlaceInTopFriends")
    public UserPlaceInLeadBoardDto getUserPlaceInFriendsLeaderBoard(@RequestBody UserPlaceInLeadBoardDto userPlaceInLeadBoardDto) {
        return userService.getUserPlaceInFriendsLeaderBoard(userPlaceInLeadBoardDto);
    }
}
