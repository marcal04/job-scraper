package com.marcal04.job_scraper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobDTO {

    private String title;
    private String company;
    private String location;
    private String url;
    private String source;


}
