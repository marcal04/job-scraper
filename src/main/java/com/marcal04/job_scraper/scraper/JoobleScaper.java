package com.marcal04.job_scraper.scraper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcal04.job_scraper.model.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class JoobleScaper implements JobScraper {


    @Value("${jooble.api.key}")
    private String apiKey;

    @Value("${jooble.api.url}")
    private String apiUrl;

    @Override
    public List<Job> scrape(String searchTerm) {
        List<Job> jobs = new ArrayList<>();

        try {

            String requestBody = String.format(
                    "{\"keywords\": \"%s\", \"location\": \"Brazil\", \"page\": \"1\", \"resultonpage\": \"20\"}",
                    searchTerm
            );


            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );


            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode vagas = root.path("jobs");

            System.out.println("Jooble — vagas encontradas: " + vagas.size());


            for (JsonNode vaga : vagas) {
                Job job = new Job();
                job.setTitle(vaga.path("title").asText("Sem título"));
                job.setCompany(vaga.path("company").asText("Não informado"));
                job.setLocation(vaga.path("location").asText("Não informado"));
                job.setUrl(vaga.path("link").asText(""));
                job.setSource(getSourceName());

                if (!job.getUrl().isEmpty()) {
                    jobs.add(job);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao acessar Jooble: " + e.getMessage());
        }

        return jobs;
    }

    @Override
    public String getSourceName() {
        return "Jooble";
    }
}