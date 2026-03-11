# 🦷Odonto Agenda

Um sistema de agendamento de consultas odontológicas desenvolvido em Java com Spring Boot e MySQL, com persistência via Docker.

## Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot 4.0
- **Banco de Dados**: MySQL 8.4.7
- **Docker**: Orquestração do banco de dados via Docker Compose
- **Bibliotecas**: Lombok, Jakarta Validation, Hibernate, JPA

## Funcionalidades

- Criação, alteração e exclusão de agendamentos
- Consulta de agendamentos por data
- Validação de horários (não permite agendamento duplicado no mesmo horário)

## Como Executar

1. Certifique-se de que o Docker está instalado e configurado.
2. Crie as variáveis de ambiente: `DB_USER`, `DB_PASSWORD`, `DB_ROOT_PASSWORD`.
3. No terminal, execute:
   ```bash
   docker-compose up
   Acesse a API no endpoint http://localhost:8080/agendamentos.

Use o Postman ou outro cliente HTTP para testar as rotas.

Endpoints

POST /agendamentos: Cria um novo agendamento.

GET /agendamentos?data=YYYY-MM-DD: Lista os agendamentos do dia.

PUT /agendamentos?cliente=...&dataHoraAgendamento=...: Atualiza um agendamento existente.

DELETE /agendamentos?cliente=...&dataHoraAgendamento=...: Deleta um agendamento.
