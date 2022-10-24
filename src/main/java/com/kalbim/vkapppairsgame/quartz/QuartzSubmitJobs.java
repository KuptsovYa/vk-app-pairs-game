package com.kalbim.vkapppairsgame.quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {


    //Stays here to remind how to do it
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";
    private static final String CRON_MORNING_JOB_TIMING = "0 0 10 ? * 1-7";
    private static final String CRON_EVENING_JOB_TIMING = "0 0 22 ? * 1-7";

    @Bean(name = "UpdateGameJobMorning")
    public JobDetailFactoryBean jobUpdateGameCountMorning() {
        return QuartzConfig.createJobDetail(UpdateGameCountJob.class, "UpdateGameCountJobMorning");
    }

    @Bean(name = "UpdateGameJobEvening")
    public JobDetailFactoryBean jobUpdateGameCountEvening() {
        return QuartzConfig.createJobDetail(UpdateGameCountJob.class, "UpdateGameCountJobEvening");
    }

    @Bean(name = "UpdateGameTriggerMorning")
    public CronTriggerFactoryBean triggerUpdateGameCountMorning(@Qualifier("UpdateGameJobMorning") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_MORNING_JOB_TIMING, "UpdateGameJobMorning");
    }

    @Bean(name = "UpdateGameTriggerEvening")
    public CronTriggerFactoryBean triggerUpdateGameCountEvening(@Qualifier("UpdateGameJobEvening") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVENING_JOB_TIMING, "UpdateGameJobEvening");
    }
}
