package com.marcal04.job_scraper.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title; //estagio
    private String company; //empresa "x"
    private String location; //são paulo
    private String source; //programathor

    @Column(length = 500)
    private String url; //link da vaga

    @Column(length = 3000)
    private String description; //descrição da vaga

    @Column(unique = true) //não dublica no banco
    private String hash; //url da vaga

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
