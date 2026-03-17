package com.marcal04.job_scraper.service;

import com.marcal04.job_scraper.model.Job;
import com.marcal04.job_scraper.repository.JobRepository;
import com.marcal04.job_scraper.scraper.JobScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private List<JobScraper> scrapers;

    public List<Job> searchAndSave(String searchTerm) {
        List<Job> allJobs = scrapers.parallelStream()
                .flatMap(scraper -> scraper.scrape(searchTerm).stream())
                .collect(Collectors.toList());

        System.out.println("Total de vagas encontradas: " + allJobs.size());

        int novas = 0;
        for (Job job : allJobs) {
            String hash = DigestUtils.md5DigestAsHex(job.getUrl().getBytes());
            job.setHash(hash);

            if (!jobRepository.existsByHash(hash)) {
                jobRepository.save(job);
                novas++;
            }
        }
        System.out.println("Vagas novas adicionadas: " + novas);
        return allJobs;

    }


}
