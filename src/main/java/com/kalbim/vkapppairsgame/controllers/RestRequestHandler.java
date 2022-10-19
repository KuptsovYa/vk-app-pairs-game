package com.kalbim.vkapppairsgame.controllers;


import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.dto.TopPlayersDto;
import com.kalbim.vkapppairsgame.dto.UserDto;
import com.kalbim.vkapppairsgame.service.PromoService;
import com.kalbim.vkapppairsgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestRequestHandler {

    private final UserService userService;
    private final PromoService promoService;

    @Autowired
    public RestRequestHandler(UserService userService, PromoService promoService) {
        this.userService = userService;
        this.promoService = promoService;
    }

    @GetMapping("v1/api/getUserData/{vkUserId}")
    public UserDto getAllUserData(@PathVariable String vkUserId) {
        return userService.getAllDataOfUser(vkUserId);
    }

    @Transactional
    @GetMapping("v1/api/getPromo/{coins}")
    public PromoDto getPromoByCoins(@PathVariable String coins) {
        return promoService.returnPromo(coins);
    }

    @PostMapping("v1/api/up")
    public void updateUserData(UserDto userDto) {
        userService.updateUserData(userDto);
    }

    @GetMapping("v1/api/getTopPlayers")
    public TopPlayersDto getTopPlayers() {
        return userService.getTopPlayers();
    }
}
