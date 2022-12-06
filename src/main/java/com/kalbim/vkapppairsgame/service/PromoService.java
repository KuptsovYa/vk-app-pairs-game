package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.PromoDto;
import com.kalbim.vkapppairsgame.dto.PlayerCoinsDto;
import com.kalbim.vkapppairsgame.dto.UserPromoDto;
import com.kalbim.vkapppairsgame.entity.PromoEntity;
import com.kalbim.vkapppairsgame.entity.UsersEntity;
import com.kalbim.vkapppairsgame.repos.PromoRepos;
import com.kalbim.vkapppairsgame.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface PromoService{
    public PromoDto returnPromo(PlayerCoinsDto playerCoinsDto);
    public UserPromoDto getUsersPromoList(UserPromoDto userPromoDto);
}
