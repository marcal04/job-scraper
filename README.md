# Job Scraper 🔍

Agregador automático de vagas de emprego para desenvolvedores.

Busca vagas simultaneamente em:
- Programathor
- Vagas.com
- Jooble (Indeed, LinkedIn e outros)

## Como rodar

### Pré-requisitos
- Java 17+
- PostgreSQL
- Maven

### Configuração

1. Clone o repositório
2. Crie o banco de dados:
```sql
CREATE DATABASE jobscraper;
```
3. Copie o arquivo de configuração:
```
cp application.properties.example src/main/resources/application.properties
```
4. Preencha os dados do banco e a chave do Jooble no `application.properties`
5. Rode o projeto:
```
mvn spring-boot:run
```
6. Acesse: `http://localhost:8080`

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- PostgreSQL
- Jsoup (web scraping)
- Jooble API