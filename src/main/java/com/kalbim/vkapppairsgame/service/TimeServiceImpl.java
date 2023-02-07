package com.kalbim.vkapppairsgame.service;

import com.kalbim.vkapppairsgame.dto.ServerTimeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Service
public class TimeServiceImpl implements TimeService{

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static Logger log = LoggerFactory.getLogger(TimeServiceImpl.class);

    @Override
    public ServerTimeDto returnServerTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return ServerTimeDto.builder().serverTime(SIMPLE_DATE_FORMAT.format(timestamp)).build();
    }
}
