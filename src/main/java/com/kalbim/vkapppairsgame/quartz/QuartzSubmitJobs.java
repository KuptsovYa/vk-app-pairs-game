package com.kalbim.vkapppairsgame.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {

    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";

    @Bean(name = "UpdateGameJob")
    public JobDetailFactoryBean jobUpdateGameCount() {
        return QuartzConfig.createJobDetail(UpdateGameCountJob.class, "Update Game count Job");
    }

    @Bean(name = "UpdateGameTrigger")
    public CronTriggerFactoryBean triggerUpdateGameCount(@Qualifier("UpdateGameJob") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_FIVE_MINUTES, "UpdateGameJob");
    }
}
