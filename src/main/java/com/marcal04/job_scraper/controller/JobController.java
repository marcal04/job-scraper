package com.marcal04.job_scraper.controller;

import com.marcal04.job_scraper.dto.JobDTO;
import com.marcal04.job_scraper.model.Job;
import com.marcal04.job_scraper.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping
    public List<JobDTO> search(@RequestParam String term) {
        List<Job> jobs = jobService.searchAndSave(term);
        return jobs.stream()
                .map(job -> new JobDTO(
                        job.getTitle(),
                        job.getCompany(),
                        job.getLocation(),
                        job.getUrl(),
                        job.getSource()
                ))
                .collect(Collectors.toList());
    }
}

