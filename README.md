# First-Decision

Este repositório contém o projeto **First-Decision**, composto por:

* **rh-api**: API em Spring Boot (Java 17) utilizando PostgreSQL e Docker.
* **frontend** (rh-web): aplicação Angular (v15+) servida em container Docker com `ng serve`.

---

## Tecnologias

* **Backend**: Java 17, Spring Boot 3.5, Spring Data JPA, PostgreSQL 15, Docker.
* **Frontend**: Angular, TypeScript, Bootstrap, Docker (`ng serve` em modo dev ou build + Nginx em prod).
* **Orquestração**: Docker Compose v3.8.

---

## Pré-requisitos

* Git
* Docker & Docker Compose
* (Opcional) Java 17, Maven — para rodar localmente sem containers
* (Opcional) Node.js 18+, Angular CLI — para rodar o frontend sem container

---

## Estrutura de pastas

```
first-decision/
├── rh-api/       # Backend Spring Boot
├── rh-web/     # Frontend Angular
├── docker-compose.yml    # Docker compose
└── README.md     # Este arquivo
```

---

## Executando com Docker Compose

A maneira mais simples é usar Docker Compose para subir todos os serviços:

```bash
# Na raiz do projeto
git clone https://github.com/seu-usuario/first-decision.git
cd first-decision

docker compose up --build
(ou docker-compose up --build)
```

Isso irá:

1. **DB** (`db`): container PostgreSQL v15 na porta **5432**, base `rh_db`.
2. **Adminer** (`adminer`): ferramenta de administração em [http://localhost:8081](http://localhost:8081).
3. **API** (`app`): Spring Boot em [http://localhost:8080/v1/users](http://localhost:8080/v1/users).
4. **Web** (`web`): Angular em [http://localhost:4200](http://localhost:4200) (modo dev com `ng serve`).

> **Nota**: se usar o build de produção (Nginx), ajuste o serviço `web` para usar o Dockerfile multi-stage.

Para parar e remover containers:

```bash
docker compose down
```

---

## Executando localmente (sem Docker)

### 1. Backend (API)

```bash
cd rh-api
# configure src/main/resources/application.properties se necessário
./mvnw spring-boot:run
```

A API ficará disponível em [**http://localhost:8080/v1/users**](http://localhost:8080/v1/users).

### 2. Frontend (Angular)

```bash
cd frontend
npm install
ng serve --open
```

O app abre automaticamente em [**http://localhost:4200**](http://localhost:4200).

---

## Testes

No backend:

```bash
cd rh-api
./mvnw test
```

No frontend:

```bash
cd frontend
ng test
```

---

## Boas práticas

* **Validação de formulários**: já implementa checagem de senha/confirmacaoSenha.
* **Tratamento de erros**: interceptor global exibe alertas em falhas de rede.
* **CORS**: liberado para [http://localhost:4200](http://localhost:4200) via `@CrossOrigin` ou `WebMvcConfigurer`.

---

## Contato

Desenvolvido por Igor Gabriel. Para dúvidas ou sugestões, abra uma issue ou envie um PR.
