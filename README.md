# 🔍 Job Scraper

Agregador automático de vagas de emprego para desenvolvedores.

🌐 **Acesse agora:** [job-scraper-production-06c8.up.railway.app](https://job-scraper-production-06c8.up.railway.app)

---

## Sobre o projeto

O Job Scraper resolve um problema real: vagas de tecnologia estão espalhadas em vários sites diferentes. Em vez de acessar cada plataforma manualmente todos os dias, esse sistema busca automaticamente em múltiplas fontes e apresenta tudo em um único lugar.

## Funcionalidades

- Busca simultânea em múltiplas plataformas
- Remoção automática de vagas duplicadas via hash MD5
- Monitoramento automático a cada hora via Scheduler
- Interface web simples e direta
- API REST para integração futura

## Fontes de vagas

| Plataforma | Método |
|---|---|
| Programathor | Web Scraping (Jsoup) |
| Vagas.com | Web Scraping (Jsoup) |
| Jooble | API REST (cobre Indeed, LinkedIn e outros) |

## Tecnologias

**Backend**
- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Scheduler
- Jsoup 1.17

**Banco de dados**
- PostgreSQL

**Deploy**
- Railway

## Arquitetura
```
Controller → Service → Scraper(s) → Repository → PostgreSQL
                ↑
           Scheduler (executa a cada 1h automaticamente)
```

Cada scraper implementa a interface `JobScraper`, permitindo adicionar novas fontes sem alterar o resto do sistema.

## Como rodar localmente

**Pré-requisitos**
- Java 17+
- PostgreSQL
- Maven

**Passos**

1. Clone o repositório
```bash
git clone https://github.com/SEU_USUARIO/job-scraper.git
cd job-scraper
```

2. Crie o banco de dados
```sql
CREATE DATABASE jobscraper;
```

3. Configure o `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobscraper
spring.datasource.username=postgres
spring.datasource.password=suasenha
jooble.api.key=SUA_CHAVE_JOOBLE
```

4. Rode o projeto
```bash
mvn spring-boot:run
```

5. Acesse `http://localhost:8080`

## Como conseguir a chave do Jooble

Acesse [jooble.org/api/about](https://jooble.org/api/about) e solicite uma chave gratuita.

## Autor

Desenvolvido por **marcal04**

[![GitHub](https://img.shields.io/badge/GitHub-marcal04-black?logo=github)](https://github.com/SEU_USUARIO)