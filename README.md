
## Descrição

Este projeto é uma API para a gestão de tarefas que permite aos usuários criar, atualizar, excluir e consultar tarefas de forma eficiente. A API é projetada seguindo os princípios de arquitetura limpa e utilizando tecnologias modernas como Spring Boot, PostgreSQL e Docker. Este projeto foi desenvolvido como parte de um teste técnico para uma vaga de desenvolvedor Java.

## Características

- **Gestão de tarefas:** Criar, ler, atualizar e excluir tarefas.
- **API RESTful:** Interface amigável que segue as melhores práticas de REST.
- **Persistência de dados:** Uso de PostgreSQL para armazenamento persistente de tarefas.
- **Contenerização:** O projeto está preparado para ser executado em um ambiente Docker, facilitando seu deploy.
- **Arquitetura limpa:** A arquitetura do projeto segue o enfoque da arquitetura hexagonal para alta manutenibilidade e escalabilidade.

## Tecnologias utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **PostgreSQL 15**
- **Docker**
- **Docker Compose**
- **Maven**

## Estrutura do projeto

O projeto segue a estrutura padrão de um projeto Spring Boot com uma arquitetura hexagonal:


```bash
src/
|-- main/
|   |-- java/
|   |   |-- com.crja.tasks_mngr_api/   # Código fonte principal
|   |       |-- application/           # Lógica de aplicação (casos de uso)
|   |       |-- domain/                # Entidades de domínio e lógica de negócio
|   |       |-- infrastructure/        # Adaptadores e configuração
|   |       |-- TaskManagerApplication.java   # Classe principal
|   |
|   |-- resources/
|       |-- application.yml            # Configurações da aplicaçã
|-- test/                               # Testes unitários e integração
=======


## Configuração e execução

### Requisitos prévios

** Docker
** Docker Compose
** JDK 17

### Configuração do banco de dados

O banco de dados PostgreSQL é configurado automaticamente usando Docker Compose. Os detalhes da configuração estão no arquivo``docker-compose.yml``.

### Iniciar o projeto

1. Clone o repositório:

```bash

    git clone https://github.com/rtase1984/task-manager-api.git

    cd task-manager-api 
```
2. 
```bash  
 mvn clean package -DskipTests
 ```

3. Construa e inicie os contêineres com Docker Compose:

```bash 
    docker-compose up --build 
```


A aplicação estará disponível em http://localhost:8080.

### Documentação com Swagger

Para consultar os endpoints disponíveis e testar a API, você pode usar o Swagger. Após iniciar o projeto, acesse http://localhost:8080/swagger-ui.html em seu navegador. A interface do Swagger irá exibir todos os endpoints da API, permitindo visualizar e interagir com eles de forma intuitiva.



