package com.marcal04.job_scraper.scraper;

import com.marcal04.job_scraper.model.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VagasScraper implements JobScraper {

    @Override
    public List<Job> scrape(String searchTerm) {
        List<Job> jobs = new ArrayList<>();

        try {

            String termSimplificado = searchTerm
                    .split(",")[0]
                    .split(" ")[0]
                    .toLowerCase()
                    .trim()
                    .replace("ã", "a")
                    .replace("ç", "c")
                    .replace("é", "e")
                    .replace("ê", "e")
                    .replace("á", "a")
                    .replace("ó", "o")
                    .replace("í", "i");

            String url = "https://www.vagas.com.br/vagas-de-" + termSimplificado;

            System.out.println("Vagas.com — URL acessada: " + url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(15_000)
                    .get();


            Elements cards = doc.select("li.vaga");
            System.out.println("Vagas.com — vagas encontradas: " + cards.size());

            for (Element card : cards) {


                Element linkEl = card.select("a.link-detalhes-vaga").first();
                if (linkEl == null) continue;

                String title = linkEl.attr("title").trim();
                String href = linkEl.attr("href").trim();


                String company = card.select("span.emprVaga").text().trim();


                String location = card.select("div.vaga-local").text()
                        .replace("Localização não informada", "Não informado")
                        .trim();

                Job job = new Job();
                job.setTitle(title);
                job.setCompany(company.isEmpty() ? "Não informado" : company);
                job.setLocation(location.isEmpty() ? "Não informado" : location);
                job.setUrl("https://www.vagas.com.br" + href);
                job.setSource(getSourceName());

                if (!job.getTitle().isEmpty()) {
                    jobs.add(job);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao acessar Vagas.com: " + e.getMessage());
        }

        return jobs;
    }

    @Override
    public String getSourceName() {
        return "Vagas.com";
    }
}