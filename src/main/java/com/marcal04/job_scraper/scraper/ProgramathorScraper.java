package com.marcal04.job_scraper.scraper;

import com.marcal04.job_scraper.model.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProgramathorScraper implements JobScraper {

    private static final String BASE_URL = "https://programathor.com.br/jobs/";

    @Override
    public List<Job> scrape(String searchTerm) {
        List<Job> jobs = new ArrayList<>();

        try {
            String url = "https://programathor.com.br/jobs?search=" +
                    searchTerm.replace(" ", "+");

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10_000)
                    .get();

            Elements jobCards = doc.select("div.cell-list");
            System.out.println("Programathor — vagas encontradas: " + jobCards.size());

            for (Element card : jobCards) {
                Job job = new Job();


                Element titleEl = card.select("h3.text-24").first();
                if (titleEl != null) {
                    titleEl.select("span").remove();
                    job.setTitle(titleEl.text().trim());
                }


                job.setCompany(getIconText(card, "fa-briefcase"));


                String location = getIconText(card, "fa-map-marker-alt");
                if (location.equals("Não informado")) {
                    location = getIconText(card, "fa-map-marker");
                }
                job.setLocation(location);


                String href = card.select("a").attr("href");
                job.setUrl("https://programathor.com.br" + href);

                job.setSource(getSourceName());

                if (job.getTitle() != null && !job.getTitle().isEmpty()) {
                    jobs.add(job);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao acessar Programathor: " + e.getMessage());
        }

        return jobs;
    }

    @Override
    public String getSourceName() {
        return "Programathor";
    }

    private String getIconText(Element card, String iconClass) {
        Element icon = card.select("i." + iconClass).first();
        if (icon != null) {
            return icon.parent().text().trim();
        }
        return "Não informado";
    }

}

