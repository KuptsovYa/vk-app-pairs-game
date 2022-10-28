package com.kalbim.vkapppairsgame.controllers;


import com.kalbim.vkapppairsgame.dto.*;
import com.kalbim.vkapppairsgame.service.PromoService;
import com.kalbim.vkapppairsgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class RestRequestHandler {

    private final UserService userService;
    private final PromoService promoService;

    @Autowired
    public RestRequestHandler(UserService userService, PromoService promoService) {
        this.userService = userService;
        this.promoService = promoService;
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
}
