# 🧠 Habit Tracker - Backend

Back-end do aplicativo **Habit Tracker**, uma aplicação para monitorar hábitos diários e acompanhar o progresso pessoal ao longo do tempo.

Este projeto utiliza **Spring Boot**, **Spring Data JPA**, **Flyway** para versionamento do banco de dados e **PostgreSQL** como banco relacional.

---
## 🚀 Tecnologias
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Maven

---
## 🛠️ Como rodar o projeto

### 1. Subir o banco de dados PostgreSQL com Docker

```bash
docker run --name habit-tracker-db \
  -e POSTGRES_DB=habit_tracker_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:15
```

ou 
```bash
docker run --name habit-tracker-db -e POSTGRES_DB=habit_tracker_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15
```

### Executar a aplicação
Usando o Maven:

```bash
./mvnw spring-boot:run
```
Ou rodar pela sua IDE (IntelliJ, VS Code, etc.) com a classe principal ``HabitTrackerBackApplication``.

---
## Estrutura dos Repositórios
Este projeto faz parte de um sistema maior e está organizado como submódulo:
- calangio_system (repositório principal)
  - calangio_system_back (este projeto)
  - calangio_system_front