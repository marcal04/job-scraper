package com.marcal04.job_scraper.repository;

import com.marcal04.job_scraper.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

// tradução do SQL automaticamente <:
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByHash(String hash);
}
