package com.kalbim.vkapppairsgame.quartz;

import com.kalbim.vkapppairsgame.service.UserService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class UpdateGameCountJob implements Job {

    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        userService.updateGameCount();
        try {
            userService.sendNotifications();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
