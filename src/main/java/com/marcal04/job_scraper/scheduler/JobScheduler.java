package com.marcal04.job_scraper.scheduler;

import com.marcal04.job_scraper.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JobScheduler {

    @Autowired
    private JobService jobService;

    @Scheduled(cron = "0 0 * * * *")
    public void monitorarvagas() {
        System.out.println("Scheduler iniciando buscas: " + LocalDateTime.now());
        jobService.searchAndSave("java");
        System.out.println("Scheduler finalizando buscas: " + LocalDateTime.now());
    }
}
