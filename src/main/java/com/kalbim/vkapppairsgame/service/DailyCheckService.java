package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.entity.DailyCheckEntity;
import com.kalbim.vkapppairsgame.repos.DailyCheckRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class DailyCheckService {

    private static final Integer DAILY_CHALLENGE_MAX_VAL = 4;
    private static final Integer MULTI = 2;
    private static final Integer DEF_MULTI = 1;
    private final DailyCheckRepo dailyCheckRepo;
    private static Logger log = LoggerFactory.getLogger(TimeServiceImpl.class);


    @Autowired
    public DailyCheckService(DailyCheckRepo dailyCheckRepo) {
        this.dailyCheckRepo = dailyCheckRepo;
    }

    public Integer getMultiplier(String user) {
        DailyCheckEntity entity = dailyCheckRepo.getCurrentLastLoginEntity(user);
        
        if (DAILY_CHALLENGE_MAX_VAL.equals(entity.getDayCount())) {
            dailyCheckRepo.insertNewLastLoginDate(user, Timestamp.valueOf(LocalDateTime.now()), DAILY_CHALLENGE_MAX_VAL);
            return MULTI;
        }
        return DEF_MULTI;
    }

    public Integer updateDailyCheckinValue(String user) {
        DailyCheckEntity entity = dailyCheckRepo.getCurrentLastLoginEntity(user);

        if (entity == null) {
            dailyCheckRepo.insertNewLastLoginDate(user, Timestamp.valueOf(LocalDateTime.now()), 0);
            return 0;
        }

        int currentCounterValue = entity.getDayCount();
        log.info("Current counter value: {}, user {}", currentCounterValue, entity.getUser());
        if (currentCounterValue > DAILY_CHALLENGE_MAX_VAL) {
            dailyCheckRepo.insertNewLastLoginDate(user, Timestamp.valueOf(LocalDateTime.now()), 0);
            return 0;
        }

        if (isDailyChallengeCompleted(entity)) {
            currentCounterValue++;
            log.info("DailyChallenge completed value: {}, user {}", currentCounterValue, entity.getUser());
            dailyCheckRepo.insertNewLastLoginDate(user, Timestamp.valueOf(LocalDateTime.now()), currentCounterValue);
            return currentCounterValue;
        }

        log.info("Just insert last login date value: {}, user {}, time {}", currentCounterValue, entity.getUser(), Timestamp.valueOf(LocalDateTime.now()));
        dailyCheckRepo.insertNewLastLoginDate(user, Timestamp.valueOf(LocalDateTime.now()), currentCounterValue);
        return currentCounterValue;
    }

    private boolean isDailyChallengeCompleted(DailyCheckEntity entity) {
        LocalDate currentLocalDate = LocalDate.now();
        LocalDate lastLoginTime = entity.getLastLoginDate().toLocalDateTime().toLocalDate();
        log.info("isDailyChallengeCompleted: current {}, last login time {}", currentLocalDate, lastLoginTime);
        return currentLocalDate.minusDays(1).isEqual(lastLoginTime);
    }
}
