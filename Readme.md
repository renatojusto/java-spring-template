# Java Architecture - Template Stack
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.arch:stock&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.arch%3Astock)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.arch:stock&metric=coverage)](https://sonarcloud.io/dashboard?id=com.arch%3Astock)
[![Build](https://img.shields.io/github/workflow/status/renatojusto/java-spring-template/Java%20CI%20with%20Maven.svg)](https://github.com/renatojusto/java-spring-template/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![Docker](https://img.shields.io/docker/v/renatoalejusto/archstock.svg)](https://hub.docker.com/repository/docker/renatoalejusto/archstock)

> Projeto template para ser utilizado no desenvolvimento de outros projetos.

## Stack

O projeto está desenvolvido com a seguinte stack:

* Java 1.8 com Adoptopenjdk
* Maven e Maven Wrapper 3.6.3
* Spring Boot 2.3.4 como Framework
* Spring Web para construir Restful APIs
* Spring Hateoas para retornar os links
* Spring Validation para validação utilizando Bean Validation 
* Spring Data JPA 2.3.4 e Hibernate como abstração da camada de dados
* Spring MockMvc para testes integrados
* MapStruct para mapeamento de objetos
* Undertown como web server
* Liquibase para migração de base de dados
* Logback como framework de log
* MySQL 8.0.19 ou Postgres como banco de dados
* JUnit5 para testes unitários
* Mockito para Mock de testes
* TestContainers para rodar o banco de testes integrados
* ArchUnit para testes da arquitetura do sistema
* Sonar para qualidade de código
* Spring Actuator, Micrometer, Prometheus e Grafana para monitoramento
* Github para hospedagem do código
* GoogleContainerTools/Jib para construir a imagem do Docker
* Docker para rodar como container
* Dockerhub para hospedagem da imagem
* Github Actions para CI
* Spring Doc para Documentação utilizando a especificação OpenAPI V3

##  Arquitetura
Este projeto está construido no modelo de camadas e utiliza o ArchUnit para validação da estrutura do projeto, sendo:

* Camada de Apresentação/Rest: pacote resource
* Camada de Negócio: pacotes service, entity, domain
* Camada de Dados: pacote repository
* Ligação entre camadas: pacotes mappers ou adapters

As datas utilizam o formato UTC, a aplicação frontend deverá formatar conforme o horário do cliente.

### Link úteis e boas práticas:

A seguir alguns links úteis ou boas práticas para o projeto:

* https://martinfowler.com/architecture/
* https://www.oracle.com/java/technologies/javase/seccodeguide.html
* https://12factor.net/pt_br/
* https://owasp.org/
* https://java-design-patterns.com/patterns/
* https://martinfowler.com/articles/practical-test-pyramid.html
* https://refactoring.com/catalog/
* https://sourcemaking.com/
* https://restfulapi.net/http-methods/
* https://github.com/microsoft/api-guidelines/blob/vNext/Guidelines.md
* https://github.com/OAI/OpenAPI-Specification
* https://www.conventionalcommits.org/pt-br/v1.0.0-beta.4

## Versionamento do projeto e criação de novas branchs.

O projeto deve seguir o modelo de [Gitflow](https://www.atlassian.com/br/git/tutorials/comparing-workflows/gitflow-workflow)

## Setup de desenvolvimento

Após realizar o clone do projeto do [Github](https://github.com/renatojusto/poupachef-stock.git) para rodar o build do projeto faça:

No Linux ou Git Bash:
```
./mvnw verify 
```

No Windows:
```
mvnw verify 
```

Para executar todos os testes, incluso de integração:

No Linux ou Git Bash:
```
./mvnw clean verify -Pintegration-test
```

No Windows:
```
mvnw clean verify -Pintegration-test
```

## Rodando o Projeto Local

#### Subir o MySQL:
```
docker-compose -f src/main/docker/mysql.yml up -d
```

####  Rodar o projeto Local:

No Linux ou Git Bash:
```
./mvnw spring-boot:run
```

No Windows:
```
mvnw spring-boot:run
```

## Rodando com Docker

Para rodar o projeto com Docker seguir os procedimentos do documento anexo [Docker](Readme-Docker.md)

## Sonar

Para rodar o sonar na aplicação executar:

No Linux ou Git Bash:
```
./mvnw sonar:sonar -Dsonar.login=${SONAR.TOKEN}
```

No Windows:
```
mvnw sonar:sonar -Dsonar.login=${SONAR.TOKEN}
```

A aplicação está disponível no [Sonar Cloud](https://sonarcloud.io/dashboard?id=com.poupachef%3Astock)

## Deploy da Aplicação

### AWS

A aplicação ainda não possui um ambiente de deploy, mas o passo a passo para subida na AWS está documentado [AWS](Readme-AWS.md)

### Azure

Para realizar o deploy na Azure, pode ser utilizado o Appservice com Azure Container.

## Continuous Integration

O sistema utiliza o [Github Actions](https://github.com/renatojusto/poupachef-stock/actions) para realizar o processo de Continuous Integration.

Toda atualização da master irá rodar o processo de construção da imagem e publicação no Dockerhub.

Também será rodado o Sonar automaticamente.

## Monitoramento

A aplicação utiliza o Micrometer com Prometheus e Grafana para monitoramento.
 
Para informações de como subir o monitoramento consulte o guia [Prometheus-Grafana](Readme-Prometheus-Grafana.md)

## Endpoints e Documentação de APIs

Os endpoints para testes e a documentação das APIs segue o Padrão OpenApi V3. Para acessar utilize o link:

http://localhost:8081/stock/swagger-ui.html

## Status da Aplicação

Para validar se o serviço está no ar chamar a URL:

http://localhost:8081/management/health

## Release History

* 1.0.0
    * Lançamento primeira versão
* 0.0.1
    * Trabalho em Progresso
