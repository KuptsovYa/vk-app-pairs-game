package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.ServerTimeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class TimeServiceImpl implements TimeService{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static Logger log = LoggerFactory.getLogger(TimeServiceImpl.class);

    @Override
    public ServerTimeDto returnServerTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.info(timestamp.toString());
        return ServerTimeDto.builder().serverTime(sdf.format(timestamp)).build();
    }
}
