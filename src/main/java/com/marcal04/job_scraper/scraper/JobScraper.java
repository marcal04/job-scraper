package com.marcal04.job_scraper.scraper;

import com.marcal04.job_scraper.model.Job;

import java.util.List;

public interface JobScraper {
    List<Job> scrape(String searchTerm); //termo da busca

    String getSourceName(); //de onde vieram? NÂO SEI <:

}
